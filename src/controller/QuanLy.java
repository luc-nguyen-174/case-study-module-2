package controller;

import model.NhanVien;
import storage.DocGhiFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuanLy implements Serializable {
    List<NhanVien> quanLyNhanVien = new ArrayList<>();

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
        DocGhiFile.ghiFile(quanLyNhanVien,"manager.bin");
    }
    public void xoaNhanVien(String id){
        quanLyNhanVien.remove(Integer.parseInt(id));
        DocGhiFile.ghiFile(quanLyNhanVien,"manager.bin");
    }
    public void hienThiNhanVien(){
        for(NhanVien nhanVien : quanLyNhanVien){
            System.out.println(nhanVien);
        }
    }
}
