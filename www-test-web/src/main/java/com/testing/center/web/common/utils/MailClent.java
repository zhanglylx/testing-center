package com.testing.center.web.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;


public class MailClent {
    //发件人地址
    private String senderAddress;
    //收件人地址
    private InternetAddress[] recipientAddress;
    //发件人账户名
    private String senderAccount;
    //发件人账户密码
    private String senderPassword;
    private static final String CHARSET = "UTF-8";
    private Properties props;
    private static final long FILE_MAX_SIZE = 45 * 1024 * 1024L;
    private Logger logger = LoggerFactory.getLogger(MailClent.class);

    public MailClent(String senderAddress, String senderAccount, String senderPassword, Properties props, InternetAddress... recipientAddress) {
        this.senderAddress = senderAddress;
        this.recipientAddress = recipientAddress;
        this.senderAccount = senderAccount;
        this.senderPassword = senderPassword;
        this.props = props;
    }


    public boolean sendMail(String title, String content, File... files) {
        //1、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //2.设置调试信息在控制台打印出来
        session.setDebug(false);
        try (Transport transport = session.getTransport()) {
            //设置发件人的账户名和密码
            transport.connect(senderAccount, senderPassword);
            //创建邮件的实例对象
            Message msg = getMimeMessage(session, title, content, files);
            //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("发送邮件异常", e);
        }
        return true;
    }

    /**
     * 获得创建一封邮件的实例对象
     *
     * @param session
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    private MimeMessage getMimeMessage(Session session, String title, String content, File... files) throws MessagingException, UnsupportedEncodingException {
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress));
        // 设置文本和 附件 的关系（合成一个大的混合"节点" / Multipart ）
        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.setSubType("mixed");         // 混合关系
        //设置邮件主题
        msg.setSubject(title, CHARSET);
        /**
         * 3.设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipients(MimeMessage.RecipientType.TO, recipientAddress);
        MimeBodyPart mainBody = new MimeBodyPart();
        mimeMultipart.addBodyPart(mainBody);
        if (null != files) {
            long size = 0;
            for (File file : files) {
                if (!file.exists()) {
                    throw new IllegalArgumentException("文件不存在:" + file.getPath());
                } else if (!file.isFile()) {
                    throw new IllegalArgumentException("不是一个文件:" + file.getPath());
                }
                size += file.length();
                MimeBodyPart attachment = new MimeBodyPart();
                DataHandler dh2 = new DataHandler(new FileDataSource(file));
                attachment.setDataHandler(dh2);
                attachment.setFileName(MimeUtility.encodeText(dh2.getName()));
                mimeMultipart.addBodyPart(attachment);
            }
            if (size > FILE_MAX_SIZE) {
                throw new RuntimeException("文件总大小超过:" + FILE_MAX_SIZE + "KB，文件大小:" + size + "KB");
            }
        }
        mainBody.setContent(content, "text/html;charset=" + CHARSET);
        // 设置整个邮件的关系（将最终的混合"节点"作为邮件的内容添加到邮件对象）
        msg.setContent(mimeMultipart);
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());
        msg.saveChanges();
        return msg;
    }

}