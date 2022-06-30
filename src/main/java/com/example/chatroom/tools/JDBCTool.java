package com.example.chatroom.tools;

import com.example.chatroom.model.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

/**
 * 数据库连接工具主要内容
 *
 * @ClassName: JDBCTool
 * @Author: cjh
 * @date: 2022/6/12
 */
public class JDBCTool {
    /**
     * 得到数据连接
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            //加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            Properties properties = new Properties();
            FileInputStream fis = null;
            try {
                fis = new FileInputStream("mysql.properties");
                properties.load(fis);
                String url = "jdbc:mysql://" + properties.getProperty("host") + ":" + properties.getProperty("port") + "/" + properties.getProperty("database") + "?useUnicode=true&characterEncoding=utf-8";
                //创建连接
                conn = DriverManager.getConnection(url, properties);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 释放连接
     */
    public static void release(Connection conn, Statement pstmt, ResultSet res) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (res != null) {
            try {
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新数据 包括插入 删除 和更新
     */
    public static boolean executeInsertDeleteUpdate(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isOk = false;

        try {
            //得到连接
            conn = JDBCTool.getConnection();

            pstmt = conn.prepareStatement(sql);

            if (args != null) {
                //设置sql语句的占位符
                for (int i = 0; i < args.length; i++) {
                    pstmt.setObject(i + 1, args[i]);
                }
            }

            //影响的函数
            int n = pstmt.executeUpdate();
            if (n > 0) {
                isOk = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放连接
            JDBCTool.release(conn, pstmt, null);
        }
        return isOk;
    }

    /**
     * 检查用户是否存在
     */
    public static User getUser(String sql, Object... args) {
        User user = null;
        ResultSet res = null;
        PreparedStatement pstmt = null;
        Connection conn = JDBCTool.getConnection();
        try {
            pstmt = conn.prepareStatement(sql);

            if (args != null) {
                //设置sql语句的占位符
                for (int i = 0; i < args.length; i++) {
                    pstmt.setObject(i + 1, args[i]);
                }
            }
            res = pstmt.executeQuery();

            if (res.next()) {
                String userName = res.getString(1);
                String password = res.getString(2);
                user = new User(userName, password);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTool.release(conn, pstmt, res);
        }
        return user;
    }

}
