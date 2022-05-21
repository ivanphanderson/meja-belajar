# [Meja Belajar (A10)](https://mejabelajar.herokuapp.com/)

## Branch Master
[![pipeline status](https://gitlab.cs.ui.ac.id/AdvProg/reguler-2022/student/kelas-a/2006463004-Ivan-Phanderson/meja-belajar/meja-belajar/badges/master/pipeline.svg)](https://gitlab.cs.ui.ac.id/AdvProg/reguler-2022/student/kelas-a/2006463004-Ivan-Phanderson/meja-belajar/meja-belajar/-/commits/master)
[![coverage report](https://gitlab.cs.ui.ac.id/AdvProg/reguler-2022/student/kelas-a/2006463004-Ivan-Phanderson/meja-belajar/meja-belajar/badges/master/coverage.svg)](https://gitlab.cs.ui.ac.id/AdvProg/reguler-2022/student/kelas-a/2006463004-Ivan-Phanderson/meja-belajar/meja-belajar/-/commits/master)

## Branch Staging
[![pipeline status](https://gitlab.cs.ui.ac.id/AdvProg/reguler-2022/student/kelas-a/2006463004-Ivan-Phanderson/meja-belajar/meja-belajar/badges/staging/pipeline.svg)](https://gitlab.cs.ui.ac.id/AdvProg/reguler-2022/student/kelas-a/2006463004-Ivan-Phanderson/meja-belajar/meja-belajar/-/commits/staging)
[![coverage report](https://gitlab.cs.ui.ac.id/AdvProg/reguler-2022/student/kelas-a/2006463004-Ivan-Phanderson/meja-belajar/meja-belajar/badges/staging/coverage.svg)](https://gitlab.cs.ui.ac.id/AdvProg/reguler-2022/student/kelas-a/2006463004-Ivan-Phanderson/meja-belajar/meja-belajar/-/commits/staging)

## Anggota
2006463004 - Ivan Phanderson

2006529114 - Adillah Putri

2006473913 - Markus Leonard Wijaya

2006595993 - Adeline Sonia Sanusie


## Description
Aplikasi ini dirancang untuk kegiatan belajar dan mengajar secara online serta digunakan oleh guru dan murid. Tujuan utamanya adalah agar murid dapat memperoleh solusi untuk setiap kesulitan yang ditemukan dalam bidang studi tertentu.

## Sprint 1
1. Registration and Login
   - Registrasi dan Login sudah diimplementasi untuk setiap role.
   - Backend sudah terintegrasi dengan frontend.
2. Dashboard
   - Dashboard belum diimplementasi.
3. CRUD Course
   - Pada fitur ini, guru dapat membuat course dengan constraint 1 guru hanya dapat memiliki 1 course. Hanya guru pemilik course dan murid yang meng-enroll course tersebut yang dapat mengakses course tersebut. Guru memiliki akses tambahan untuk mengupdate ataupun menghapus course. Pada sprint 1, fitur ini sudah terimplementasi pada bagian backend dan sudah terhubung dengan frontend. Akan tetapi, fitur ini belum terintegrasi dengan murid sehingga murid belum dapat mengakses halaman course. Error handling pada sprint ini juga belum terimplementasi dengan baik.
   - Tests pada fitur ini akan dikerjakan pada sprint 3 setelah terintegrasi dengan fitur-fitur lain.
4. Course Registration
   - Fitur ini memungkinkan murid agar dapat melakukan registrasi course. Pada sprint 1, fitur ini masih diterapkan dalam bentuk backend.
   - Tests pada fitur ini akan dikerjakan pada sprint 3 setelah terintegrasi dengan fitur-fitur lain.
5. Rate a course
   - Fitur ini memungkinkan murid agar dapat memberi rate untuk course. Pada sprint 1, fitur ini masih diterapkan dalam bentuk backend.
   - Tests pada fitur ini akan dikerjakan pada sprint 3 setelah terintegrasi dengan fitur-fitur lain.
6. Validate new user accounts (teachers, students)
7. Make a form that can be filled out by the teacher (teacher's log)

## Sprint 2
1. Registration and Login
   - Penambahan validasi pattern email saat registrasi.
   - Admin akan di-redirect ke dashboardnya saat berhasil login. Untuk guru dan murid dashboard belum tersedia.
   - Admin dapat men-generate token yang bisa dipakai untuk registrasi admin.
   - Perbaikan pada url setelah berhasil logout (sebelumnya /login?logout menjadi /login).
2. Dashboard
   -  Dashboard untuk admin sudah ada.
3. CRU Course
   - Pada fitur ini, guru dapat membuat course dengan constraint 1 guru hanya dapat memiliki 1 course. Hanya guru pemilik course dan murid yang meng-enroll course tersebut yang dapat mengakses course tersebut. Guru memiliki akses tambahan untuk mengupdate course. Pada sprint 2, fitur delete course ditiadakan dengan pertimbangan untuk mencegah murid yang sudah meng-enroll course, tetapi coursenya tiba-tiba dihapus. Fitur ini juga sudah terintegrasi dengan role murid. Error handling juga sudah diperbaiki sehingga memberikan feedback yang lebih baik kepada pengguna.
   - Tests pada fitur ini akan dikerjakan pada sprint 3 setelah terintegrasi dengan fitur-fitur lain.
4. Archive Course
   - Fitur ini ditambahkan pada sprint 2 sebagai pengganti fitur delete course. Hanya guru pemilik course.yang dapat mengakses fitur ini. Ketika guru meng-archive course miliknya, maka course tersebut tidak dapat lagi di update oleh guru yang bersangkutan. Selain itu, murid juga sudah tidak dapat meng-enroll course ini lagi. Error handling terkait fitur ini sudah diimplementasikan sehingga memberi feedback yang baik kepada pengguna
   - Tests pada fitur ini akan dikerjakan pada sprint 3 setelah terintegrasi dengan fitur-fitur lain.
5. Course Notification
   - Murid akan diberikan notifikasi ketika ada informasi baru ataupun informasi yang baru diupdate oleh guru. Fitur ini baru ditambahkan pada sprint 2 dengan tujuan murid tidak ketinggalan terhadap perkembangan course yang di-enroll. Implementasi fitur ini sudah menerapkan observer pattern sehingga setiap ada create atau update pada course information, murid yang sudah ter-enroll akan dinotifikasi.
   - Tests pada fitur ini akan dikerjakan pada sprint 3 setelah terintegrasi dengan fitur-fitur lain.
6. Course Registration
   - Fitur ini sudah terhubung dengan frontend, murid dapat mengambil banyak course. Murid tidak dapat melakukan enroll course lebih dari 1 kali.
   - Tests pada fitur ini akan dikerjakan pada sprint 3 setelah terintegrasi dengan fitur-fitur lain.
7. Rate a course
   - Fitur ini sudah terhubung dengan frontend, murid dapat memberi 1 rating untuk 1 course dan rate ditampilkan pada halaman course.
   - Tests pada fitur ini akan dikerjakan pada sprint 3 setelah terintegrasi dengan fitur-fitur lain.
8. Validate new user accounts (teachers, students)
9. Make a form that can be filled out by the teacher (teacher's log)
