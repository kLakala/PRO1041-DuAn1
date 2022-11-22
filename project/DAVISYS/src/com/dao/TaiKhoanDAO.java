/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.entity.TaiKhoanEntity;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.utils.JdbcHelper;

public class TaiKhoanDAO extends DAVISY<TaiKhoanEntity, String> {

    final String INSERT_SQL = "INSERT INTO TAIKHOAN (TENDN, MACV, TENNV, EMAIL, MATKHAU,DIACHI,DIENTHOAI,NGAYSINH,GIOITINH,TRANGTHAI) values(?, ?, ?, ?, ?,?, ?, ?, ?, ?)";
    final String UPDATE_SQL = "UPDATE TAIKHOAN SET MACV = ?, TENNV = ?, EMAIL = ?, MATKHAU = ?,DIACHI = ? ,DIENTHOAI= ?,NGAYSINH = ?,GIOITINH = ? ,TRANGTHAI= ? WHERE TENDN = ?";
    final String DELETE_SQL = "DELETE FROM TAIKHOAN WHERE TENDN = ?";
    final String SELECT_ALL_SQL = "SELECT TENDN,TENNV,CHUCVU.TENCV,EMAIL,MATKHAU,DIACHI,DIENTHOAI,NGAYSINH,GIOITINH,TRANGTHAI FROM TAIKHOAN ,CHUCVU WHERE TAIKHOAN.MACV = CHUCVU.MACV";
    final String SELECT_BY_ID_SQL = "SELECT TENDN,CHUCVU.TENCV,TENNV,EMAIL,MATKHAU,DIACHI,DIENTHOAI,NGAYSINH,GIOITINH,TRANGTHAI FROM TAIKHOAN ,CHUCVU WHERE TAIKHOAN.MACV = CHUCVU.MACV AND TENDN = ?";

    @Override
    public void insert(TaiKhoanEntity entity) {
        JdbcHelper.update(INSERT_SQL, entity.getTenDN(), entity.getMaCV(), entity.getTenNV(), entity.getEmail(), entity.getMatKhau(), entity.getDiaChi(), entity.getDienThoai(), entity.getNgaySinh(), entity.isGioiTInh(), entity.isTrangThai());
    }

    @Override
    public void update(TaiKhoanEntity entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getMaCV(), entity.getTenNV(), entity.getEmail(), entity.getMatKhau(), entity.getDiaChi(), entity.getDienThoai(), entity.getNgaySinh(), entity.isGioiTInh(), entity.isTrangThai(), entity.getTenDN());
    }

    @Override
    public void delete(String key) {
        JdbcHelper.update(DELETE_SQL, key);
    }

    @Override
    public List<TaiKhoanEntity> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public TaiKhoanEntity selectById(String key) {
        List<TaiKhoanEntity> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<TaiKhoanEntity> selectBySql(String sql, Object... args) {
        List<TaiKhoanEntity> list = new ArrayList<TaiKhoanEntity>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                TaiKhoanEntity entity = new TaiKhoanEntity();
                entity.setTenDN(rs.getString("TENDN"));
//                entity.setMaCV(rs.getInt("MACV"));
                entity.setTenNV(rs.getString("TENNV"));
                entity.setTenCV(rs.getString("TENCV"));
                entity.setEmail(rs.getString("EMAIL"));
                entity.setMatKhau(rs.getString("MATKHAU"));
                entity.setDiaChi(rs.getString("DIACHI"));
                entity.setDienThoai(rs.getString("DIENTHOAI"));
                entity.setNgaySinh(rs.getDate("NGAYSINH"));
                entity.setGioiTInh(rs.getBoolean("GIOITINH"));
                entity.setTrangThai(rs.getBoolean("TRANGTHAI"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}