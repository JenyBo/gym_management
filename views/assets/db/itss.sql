drop database itss;
create database itss;
use itss;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `phong_tap` (   
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `ten_phong` varchar(30)
);

CREATE TABLE `thiet_bi` (  
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `id_phong_tap` int,
  `ten` varchar(30),
  `ngay_nhap_ve` date,
  `xuat_xu` varchar(30),
  `tinh_trang` varchar(30)
);

CREATE TABLE `nhan_vien` (  
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `id_phong_tap` int,
  `ho_va_ten` varchar(30)
);

CREATE TABLE `phan_hoi` (  
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `id_hoi_vien` int,
  `noi_dung` text,
  `hoi_dap` text
);

CREATE TABLE `hoi_vien` (  
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `ho_ten` varchar(30),
  `sinh_nhat` date,
  `nghe_nghiep` varchar(30),
  `gioi_tinh` varchar(10),
  `loai_thanh_vien` varchar(30)
);

CREATE TABLE `dang_ki_goi_tap` ( 
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `id_hoi_vien` int,
  `id_goi_tap` int,
  `ngay_dang_ki` date
);

CREATE TABLE `goi_tap` (  
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `ten_goi_tap` varchar(30),
  `so_tien` int,
  `loai_goi_tap` varchar(30)
);

CREATE TABLE `thu_phi` (  
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `id_hoi_vien` int,
  `id_goi_tap` int,
  `ngay_thu_phi` date
);

CREATE TABLE `role` (  
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `ten_role` varchar(30)
);

CREATE TABLE `tai_khoan` (  
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `tai_khoan` varchar(30),
  `mat_khau` varchar(30),
  `id_hoi_vien` int,
  `id_nhan_vien` int,
  `id_role` int
);

CREATE TABLE `lich_su` ( 
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `id_hoi_vien` int,
  `id_goi_tap` int,
  `id_phong_tap` int,
  `ngay_su_dung` date
);



ALTER TABLE `lich_su` ADD FOREIGN KEY (`id_hoi_vien`) REFERENCES `hoi_vien` (`id`) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE `dang_ki_goi_tap` ADD FOREIGN KEY (`id_hoi_vien`) REFERENCES `hoi_vien` (`id`) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE `phan_hoi` ADD FOREIGN KEY (`id_hoi_vien`) REFERENCES `hoi_vien` (`id`) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE `tai_khoan` ADD FOREIGN KEY (`id_hoi_vien`) REFERENCES `hoi_vien` (`id`) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE `thu_phi` ADD FOREIGN KEY (`id_hoi_vien`) REFERENCES `hoi_vien` (`id`) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE `tai_khoan` ADD FOREIGN KEY (`id_nhan_vien`) REFERENCES `nhan_vien` (`id`) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE `dang_ki_goi_tap` ADD FOREIGN KEY (`id_goi_tap`) REFERENCES `goi_tap` (`id`) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE `thiet_bi` ADD FOREIGN KEY (`id_phong_tap`) REFERENCES `phong_tap` (`id`) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE `nhan_vien` ADD FOREIGN KEY (`id_phong_tap`) REFERENCES `phong_tap` (`id`) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE `tai_khoan` ADD FOREIGN KEY (`id_role`) REFERENCES `role` (`id`) ON UPDATE CASCADE ON DELETE CASCADE;


INSERT INTO role (id, ten_role) VALUES 
(1, "Chủ phòng gym"),
(2, "Nhân viên quản lý"),
(3, "Huấn luyện viên"),
(4, "Nhân viên"),
(5, "Hội viên");

INSERT INTO phong_tap (id, ten_phong) VALUES 
(1, "Cơ sở Trần Duy Hưng"),
(2, "Cơ sở Tạ Quang Bửu"),
(3, "Cơ sở Quất Lâm"),
(4, "Cơ sở Trần Đại Nghĩa"),
(5, "Cơ sở Nguyễn Trãi 1"),
(6, "Cơ sở Nguyễn Trãi 2");

INSERT INTO nhan_vien (id_phong_tap, ho_va_ten) VALUES
(1, "Hoàng Danh Quân"),
(2, "Trần Tiến Ngọc"),
(6, "Phạm Quang Nhật"),
(3, "Nguyễn Hùng Tiến"),
(5, "Nguyễn Tuấn Thành"),
(6, "Nguyễn Tuấn Toàn"),
(2, "Hoàng Văn Chiến"),
(3, "Nguyễn Danh Công"),
(4, "Vũ Xuân Đài"),
(4, "Nguyên Hồng"),
(2, "Trần Đại La"),
(1, "Nguyễn Thị Thanh Thúy"),
(5, "Nguyễn Xuân Hồng"),
(2, "Hoàng Thị Thanh Nga"),
(3, "Nguyễn Tuấn Hưng"),
(4, "Phạm Thị Hải Yến");

INSERT INTO `hoi_vien`(`ho_ten`, `sinh_nhat`, `loai_thanh_vien`, `gioi_tinh`, `nghe_nghiep`) VALUES 
("Hoàng Như Nghĩa", "2002-12-01", "VIP1", "Nam", "Developer"),
("Hoàng Vân Trường", "2002-11-09", "VIP1", "Nữ", "Coder"),
("Nguyễn Quang Trường", "2002-11-11", "VIP2", "Nam", "Game thủ"),
("Ngô Lan Anh", "1999-12-01", "VIP1", "Nữ", "Giáo viên"),
("Lương Phương Liên", "1998-12-01", "VIP1", "Nữ", "Giáo sư"),
("Phạm Bích Phương", "1967-12-01", "VIP3", "Nữ", "Giáo viên"),
("Trần Minh Hiếu", "1985-06-15", "VIP3", "Nam", "Kỹ sư"),
("Nguyễn Thị Anh", "1990-09-20", "VIP3", "Nữ", "Luật sư"),
("Lê Văn Tùng", "1978-03-10", "VIP3", "Nam", "Chủ doanh nghiệp"),
("Hoàng Thị Linh", "1982-11-27", "VIP3", "Nữ", "Nhân viên văn phòng"),
("Đỗ Văn Tuấn", "1989-07-08", "VIP3", "Nam", "Kỹ thuật viên"),
("Trần Thị Hương", "1986-02-14", "VIP3", "Nữ", "Diễn viên"),
("Nguyễn Minh Dương", "1975-09-05", "VIP3", "Nam", "Kế toán viên"),
("Vũ Thị Trang", "1988-12-25", "VIP3", "Nữ", "Dược sĩ"),
("Phan Văn An", "1980-04-18", "VIP3", "Nam", "Nhà thiết kế"),
("Nguyễn Thị Hà", "1973-01-07", "VIP3", "Nữ", "Người mẫu"),
("Lê Minh Tuấn", "1992-08-16", "VIP3", "Nam", "Giám đốc marketing"),
("Trần Thị Mai", "1984-05-12", "VIP3", "Nữ", "Dược phẩm"),
("Lê Văn Quang", "1979-10-30", "VIP3", "Nam", "Giáo viên mầm non"),
("Phạm Thị Hương", "1987-03-25", "VIP3", "Nữ", "Chuyên viên tư vấn"),
("Nguyễn Văn Hải", "1981-06-09", "VIP3", "Nam", "Bộ đội"),
("Trần Thanh Hà", "1995-02-22", "VIP3", "Nữ", "Nhà văn"),
("Đỗ Văn Dương", "1983-07-31", "VIP3", "Nam", "Diễn viên hài"),
("Lê Thị Mai", "1991-09-03", "VIP3", "Nữ", "Lễ tân khách sạn"),
("Nguyễn Văn Nam", "1977-12-08", "VIP3", "Nam", "Nhà báo"),
("Trần Minh Tuấn", "2009-02-02", "VIP3", "Nam", "Sinh viên");

INSERT INTO `goi_tap`(`id`, `ten_goi_tap`, `so_tien`, `loai_goi_tap`) VALUES 
(1, "Gói Gym cơ bản", "60000", "Theo ngày"),
(2, "Gói Gym cơ bản", "1500000", "Theo tháng"),
(3, "Gói Gym cơ bản", "9000000", "Theo năm"),
(4, "Gói Gym nâng cao", "900000", "Theo ngày"),
(5, "Gói Gym nâng cao", "2000000", "Theo tháng"),
(6, "Gói Gym nâng cao", "9000000", "Theo năm"),
(7, "Gói Gym siêu cấp", "120000", "Theo ngày"),
(8, "Gói Gym siêu cấp", "2100000", "Theo tháng"),
(9, "Gói Gym siêu cấp", "12000000", "Theo năm");

INSERT INTO `thiet_bi`(`id_phong_tap`, `ten`, `ngay_nhap_ve`, `xuat_xu`, `tinh_trang`) VALUES 
(1, "Máy chạy bộ", "2022-12-11", "Trung Quốc", "Còn sử dụng được"),
(1, "Máy chạy bộ", "2022-12-12", "Trung Quốc", "Còn sử dụng được"),
(1, "Máy tập chân", "2022-12-11", "Việt Nam", "Còn sử dụng được"),
(5, "Máy tập chân", "2022-12-11", "Việt Nam", "Còn sử dụng được"),
(5, "Máy tập chân", "2022-12-11", "Việt Nam", "Còn sử dụng được"),
(6, "Máy tập chân", "2022-12-11", "Việt Nam", "Hỏng"),
(6, "Máy tập chân", "2022-12-11", "Việt Nam", "Còn sử dụng được"),
(1, "Tạ tay", "2022-12-11", "Trung Quốc", "Còn sử dụng được"),
(4, "Tạ tay", "2022-12-11", "Trung Quốc", "Còn sử dụng được"),
(4, "Tạ tay", "2022-12-11", "Trung Quốc", "Hỏng"),
(4, "Tạ tay", "2022-12-11", "Trung Quốc", "Còn sử dụng được"),
(1, "Tạ tay", "2022-12-11", "Trung Quốc", "Hỏng"),
(3, "Xà đơn", "2022-10-20", "Hàn Quốc", "Còn sử dụng được"),
(5, "Xà đơn", "2022-10-20", "Hàn Quốc", "Còn sử dụng được"),
(5, "Xà đơn", "2022-10-20", "Hàn Quốc", "Hỏng"),
(3, "Xà đơn", "2022-10-20", "Hàn Quốc", "Hỏng"),
(6, "Xà đơn", "2022-10-20", "Hàn Quốc", "Hỏng"),
(6, "Xà đơn", "2022-10-20", "Hàn Quốc", "Còn sử dụng được"),
(3, "Xà đơn", "2022-10-20", "Hàn Quốc", "Còn sử dụng được"),
(2, "Máy chạy bộ", "2022-12-11", "Trung Quốc", "Còn sử dụng được"),
(2, "Máy chạy bộ", "2022-12-11", "Trung Quốc", "Hỏng");

INSERT INTO `tai_khoan`(`tai_khoan`, `mat_khau`, `id_nhan_vien`, `id_role`) VALUES 
('admin', "1", 1, 1),
('quanly1', "1", 2, 2),
('huanluyenvien1', "1", 3, 3),
('nhanvien1', "1", 4, 4),
('nhanvien2', "1", 5, 4),
('quanly2', "1", 6, 2),
('huanluyenvien2', "1", 7, 3),
('nhanvien3', "1", 8, 4),
('nhanvien4', "1", 9, 4),
('nhanvien5', "1", 10, 4),
('nhanvien6', "1", 11, 4),
('nhanvien7', "1", 13, 4),
('nhanvien8', "1", 14, 4),
('nhanvien9', "1", 15, 4),
('nhanvien10', "1", 16, 4);

INSERT INTO `tai_khoan`(`tai_khoan`, `mat_khau`, `id_hoi_vien`, `id_role`) VALUES 
('hoivien1', "1", 1, 5),
('hoivien2', "1", 2, 5),
('hoivien3', "1", 3, 5),
('hoivien4', "1", 4, 5),
('hoivien5', "1", 5, 5),
('hoivien6', "1", 6, 5),
('hoivien7', "1", 7, 5),
('hoivien8', "1", 8, 5),
('hoivien9', "1", 9, 5),
('hoivien10', "1", 19, 5),
('hoivien11', "1", 11, 5),
('hoivien12', "1", 12, 5),
('hoivien13', "1", 13, 5),
('hoivien14', "1", 14, 5),
('hoivien15', "1", 15, 5),
('hoivien16', "1", 16, 5),
('hoivien17', "1", 17, 5),
('hoivien18', "1", 18, 5),
('hoivien19', "1", 19, 5),
('hoivien20', "1", 20, 5),
('hoivien21', "1", 21, 5),
('hoivien22', "1", 22, 5),
('hoivien23', "1", 23, 5),
('hoivien24', "1", 24, 5),
('hoivien25', "1", 25, 5),
('hoivien26', "1", 26, 5);

INSERT INTO `phan_hoi`(`id_hoi_vien`, `noi_dung`, `hoi_dap`) VALUES 
(1, "Phòng tập Tạ Quang Bửu quá bẩn", "OK tôi sẽ cho dọn dẹp"),
(2, "Phòng tập Tạ Quang Bửu có anh PT dễ tính quá", "Cảm ơn bạn"),
(1, "Phòng tập Trần Duy Hưng không có điều hòa nóng quá", "OK tôi sẽ cho lắp điều hòa"),
(11, "Phòng tập Trần Duy Hưng ở gần công trình xây dựng bụi bặm quá", ""),
(4, "Phòng tập Nguyễn Trãi 1 quá bẩn", ""),
(3, "Phòng tập Nguyễn Trãi 1 quá nóng", "Cảm ơn ý kiến của bạn tôi sẽ cho lắp điều hòa"),
(4, "Phòng tập Trần Duy Hưng lót sàn bị bong ra", ""),
(3, "Phòng tập Quất Lâm quá bẩn", "Nhiều người bảo sạch mà");

INSERT INTO `dang_ki_goi_tap`(`id_hoi_vien`, `id_goi_tap`, `ngay_dang_ki`) VALUES 
(1, 1, "2022-11-01"),
(2, 2, "2022-11-02"),
(3, 3, "2022-11-03"),
(4, 4, "2022-11-04"),
(5, 5, "2022-11-05"),
(6, 6, "2022-11-06"),
(7, 7, "2022-11-07"),
(8, 8, "2022-11-08"),
(9, 9, "2022-11-09"),
(10, 1, "2022-11-10"),
(11, 2, "2022-11-11"),
(12, 3, "2022-11-12"),
(13, 4, "2022-11-13"),
(14, 5, "2022-11-14"),
(15, 6, "2022-11-15"),
(16, 7, "2022-11-16"),
(17, 8, "2022-11-17"),
(18, 9, "2022-11-18"),
(19, 1, "2022-11-19"),
(20, 2, "2022-11-20"),
(21, 3, "2022-11-21"),
(22, 4, "2022-11-22"),
(23, 5, "2022-11-23"),
(24, 6, "2022-11-25"),
(25, 7, "2022-11-27"),
(26, 8, "2022-11-30");

INSERT INTO `thu_phi`(`id_hoi_vien`, `id_goi_tap`, `ngay_thu_phi`) VALUES
(1, 1, "2022-11-02"),
(2, 2, "2022-11-03"),
(3, 3, "2022-11-03"),
(4, 4, "2022-11-04"),
(5, 5, "2022-11-05"),
(6, 6, "2022-11-05"),
(7, 7, "2022-11-09"),
(8, 8, "2022-11-09"),
(9, 9, "2022-11-09"),
(10, 1, "2022-11-12"),
(11, 2, "2022-11-13"),
(12, 3, "2022-11-14"),
(13, 4, "2022-11-15"),
(14, 5, "2022-11-14"),
(15, 6, "2022-11-19"),
(16, 7, "2022-11-19");

INSERT INTO `lich_su`(`id_hoi_vien`, `id_goi_tap`, `id_phong_tap`, `ngay_su_dung`) VALUES
(1, 1, 1, "2023-07-07"),
(1, 1, 2, "2023-07-08"),
(1, 1, 2, "2023-07-09"),
(1, 1, 2, "2023-07-10"),
(1, 1, 2, "2023-07-11"),
(1, 1, 3, "2023-07-12"),
(1, 1, 1, "2023-07-13"),
(1, 1, 3, "2023-07-17"),
(1, 1, 2, "2023-07-18"),
(3, 3, 3, "2023-08-10"),
(3, 3, 3, "2023-08-11"),
(3, 3, 3, "2023-08-12"),
(3, 3, 3, "2023-08-13"),
(3, 3, 3, "2023-08-14"),
(4, 4, 1, "2023-06-11"),
(4, 4, 1, "2023-06-12"),
(4, 4, 1, "2023-06-13"),
(4, 4, 1, "2023-06-14"),
(4, 4, 1, "2023-06-15"),
(13, 4, 1, "2023-06-07"),
(13, 4, 1, "2023-06-09"),
(13, 4, 1, "2023-06-11"),
(13, 4, 1, "2023-06-15");


COMMIT;