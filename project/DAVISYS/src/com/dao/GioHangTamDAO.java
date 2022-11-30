/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.entity.GioHangTamEntity;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.utils.JdbcHelper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class GioHangTamDAO extends DAVISY<GioHangTamEntity, String> {

    final String INSERT_SQL = "INSERT INTO GIOHANGTAM (MAGH, MASP, SOLUONG) values(?,?,?)";
    final String UPDATE_SQL = "UPDATE GIOHANGTAM SET  SOLUONG = ? WHERE MAGH = ? AND MASP = ?";
    final String DELETE_SQL = "DELETE FROM GIOHANGTAM WHERE MAGH = ? AND MASP = ?";
    final String SELECT_ALL_SQL = "SELECT a.MAKH,a.HOTEN,a.DIENTHOAI,b.MAGH,f.MAHANG,g.MALH,f.TENHANG,g.TENLH,e.TENNV, d.MASP,d.TENSP,d.GIABAN,d.GIANHAP, c.SOLUONG \n"
            + "FROM KHACHHANG a ,GIOHANG b ,GIOHANGTAM c,SANPHAM d, TAIKHOAN e, HANG f,LOAIHANG g WHERE a.MAKH =b.MAKH AND b.MAGH=c.MAGH AND c.MASP =d.MASP AND b.TENDN =e.TENDN  AND d.MAHANG=f.MAHANG AND d.MALH=g.MALH";
    final String SELECT_BY_ID_SQL = "SELECT a.MAKH,a.HOTEN,a.DIENTHOAI,b.MAGH,f.MAHANG,g.MALH,f.TENHANG,g.TENLH,e.TENNV, d.MASP,d.TENSP,d.GIABAN,d.GIANHAP, c.SOLUONG \n"
            + "FROM KHACHHANG a ,GIOHANG b ,GIOHANGTAM c,SANPHAM d, TAIKHOAN e, HANG f,LOAIHANG g WHERE a.MAKH =b.MAKH AND b.MAGH=c.MAGH AND c.MASP =d.MASP AND b.TENDN =e.TENDN  AND d.MAHANG=f.MAHANG AND d.MALH=g.MALH AND a.DIENTHOAI = ?";

    @Override
    public void insert(GioHangTamEntity entity) {
        JdbcHelper.update(INSERT_SQL, entity.getMaGH(), entity.getMaSP(), entity.getSoLuong());
    }

    @Override
    public void update(GioHangTamEntity entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getSoLuong(), entity.getMaGH(), entity.getMaSP());
    }

    @Override
    public void delete(String key) {
        JdbcHelper.update(DELETE_SQL, key, key);
    }

    @Override
    public List<GioHangTamEntity> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public GioHangTamEntity selectById(String key) {
        List<GioHangTamEntity> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<GioHangTamEntity> selectByIdlist(String key) {
        List<GioHangTamEntity> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    protected List<GioHangTamEntity> selectBySql(String sql, Object... args) {
        List<GioHangTamEntity> list = new ArrayList<GioHangTamEntity>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                GioHangTamEntity entity = new GioHangTamEntity();
                entity.setMaKH(rs.getString("MAKH"));
                entity.setTenKH(rs.getString("HOTEN"));
                entity.setDienThoai(rs.getString("DIENTHOAI"));
                entity.setMaGH(rs.getString("MAGH"));
                entity.setTenNV(rs.getString("TENNV"));
                entity.setMaSP(rs.getString("MASP"));
                entity.setMaHang(rs.getString("MAHANG"));
                entity.setMaLoai(rs.getString("MALH"));
                entity.setTenHang(rs.getString("TENHANG"));
                entity.setTenLoai(rs.getString("TENLH"));
                entity.setTenSP(rs.getString("TENSP"));
                entity.setGiaNhap(rs.getFloat("GIANHAP"));
                entity.setGiaBan(rs.getFloat("GIABAN"));
                entity.setSoLuong(rs.getInt("SOLUONG"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete2(String key1, String key2) {
        JdbcHelper.update(DELETE_SQL, key1, key2);
    }

    public List<GioHangTamEntity> getAll(String sdt) {
        List<GioHangTamEntity> list = new ArrayList<>();        //tạo list chứa đối tượng tài khoản
        String userName = "sa";
        String password = "songlong";
        //String url = "jdbc:sqlserver://localhost:1433;databaseName=QLSINHVIEN";
        String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=DAVISYS;encrypt=false;";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, userName, password);
            Statement st = con.createStatement();
            String sql = "SELECT a.MAKH,a.HOTEN,a.DIENTHOAI,b.MAGH,f.MAHANG,g.MALH,f.TENHANG,g.TENLH,e.TENNV, d.MASP,d.TENSP,d.GIABAN,d.GIANHAP, c.SOLUONG\n"
                    + "            FROM KHACHHANG a ,GIOHANG b ,GIOHANGTAM c,SANPHAM d, TAIKHOAN e, HANG f,LOAIHANG g WHERE a.MAKH =b.MAKH AND b.MAGH=c.MAGH AND c.MASP =d.MASP AND b.TENDN =e.TENDN  AND d.MAHANG=f.MAHANG AND d.MALH=g.MALH AND a.DIENTHOAI = " + sdt;
            ResultSet rs = st.executeQuery(sql);
            list.clear();
            while (rs.next()) {
                String maKH = rs.getString(1);
                String hoTen = rs.getString(2);
                String dienThoai = rs.getString(3);
                String maGioHang = rs.getString(4);
                String maHang = rs.getString(5);
                String maLoaiHang = rs.getString(6);
                String tenHang = rs.getString(7);
                String tenLoaiHang = rs.getString(8);
                String tenNhanVien = rs.getString(9);

                String maSanPham = rs.getString(10);
                String tenSanPham = rs.getString(11);
                float giaBan = rs.getFloat(12);
                float giaNhap = rs.getFloat(13);
                int soLuong = rs.getInt(14);

                list.add(new GioHangTamEntity(maKH, hoTen, dienThoai, maGioHang, maHang, maLoaiHang,
                         tenHang, tenLoaiHang, tenNhanVien, maSanPham, tenSanPham, giaBan, giaNhap, soLuong * giaBan, giaBan, soLuong));//add vào list
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return list;
    }

}
