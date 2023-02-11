package controller;

import model.NhanVien;
import storage.DocGhiObj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuanLy implements Serializable {
    List<NhanVien> quanLyNhanVien;

    public QuanLy(List<NhanVien> nhanViens) {
        this.quanLyNhanVien = nhanViens;
    }

    public List<NhanVien> getNhanViens() {
        return quanLyNhanVien;
    }

    public void setNhanViens(List<NhanVien> nhanViens) {
        this.quanLyNhanVien = nhanViens;
    }
    public void themNhanViens(NhanVien nhanVien) {
        quanLyNhanVien.add(nhanVien);
        new DocGhiObj().writeFile(quanLyNhanVien, "manager.bin");
    }
    public void xoaNhanVien(String id){
        quanLyNhanVien.remove(Integer.parseInt(id));
        new DocGhiObj().writeFile(quanLyNhanVien, "manager.bin");
    }
    public void hienThiNhanVien(){
        for(NhanVien nhanVien : quanLyNhanVien){
            System.out.println(nhanVien);
        }
    }
    public void xoaTatCaNhanVien(){
        quanLyNhanVien.clear();
        new DocGhiObj().writeFile(quanLyNhanVien,"manager.bin");
    }
}
