# Assignment Day 33: API Testing with RestAssured (Advanced)

## 📌 Deskripsi
Project ini dibuat untuk memenuhi Assignment Day 33 (Deadline: 07 Desember 2025, 23:30).  
Tujuan: Melakukan pengujian API menggunakan **RestAssured** berbasis Java dengan skenario positif untuk setiap method utama (GET, POST, UPDATE, DELETE).

API yang diuji: **Sport Category API**  
Dokumentasi: [Postman API Docs](https://documenter.getpostman.com/view/11853995/2sB34fmLXS)

---

## 🛠️ Tools
- Java 17
- IntelliJ IDEA
- RestAssured
- TestNG
- Gradle/Maven
- GitHub (repository public)

---

## 📑 Test Planning
Skenario positif yang diuji:

| API Action        | Endpoint             | Method | Request Body (contoh)                  | Expected Status | Expected Response Body |
|-------------------|----------------------|--------|----------------------------------------|-----------------|------------------------|
| Get Categories    | `/categories`        | GET    | -                                      | 200             | List of categories (id, name) |
| Create Category   | `/categories`        | POST   | `{ "name": "Football" }`               | 201             | Object with `id`, `name` |
| Update Category   | `/categories/{id}`   | POST   | `{ "name": "Football Updated" }`       | 200             | Updated object with new `name` |
| Delete Category   | `/categories/{id}`   | DELETE | -                                      | 204             | No Content (empty body) |

---

## 🚀 Cara Menjalankan Test
1. Clone repository:
   ```bash
   git clone https://github.com/IAmThePX/dibimbing-AssignmentDay33/

2. Masuk ke folder project:
cd <repo-name>

3. Jalankan test:
./gradlew clean test

atau jika menggunakan Maven:
mvn test

✅ Hasil Eksekusi
Contoh hasil eksekusi:
===============================================
API Test Suite
Total tests run: 9, Passes: 9, Failures: 0, Skips: 0
===============================================
Process finished with exit code 0

📂 Struktur Projectsrc
 └── test
     └── java
         └── api
             ├── GetCategoriesTest.java
             ├── CreateCategoryTest.java
             ├── UpdateCategoryTest.java
             └── DeleteCategoryTest.java
 └── main
     └── resources
         └── json
             └── token.json
README.md
build.gradle / pom.xml
testng.xml

🔎 Reflection Questions- Masalah pertama yang ditemui
- Masalah pertama yang ditemui
Saya sempat mengalami error karena kurang tepat dalam penyebutan variabel di dalam tanda kutip (""). Kesalahan kecil pada simbol titik (.) menyebabkan pembacaan variabel gagal dan pengujian menjadi failed. Setelah memperbaiki penulisan simbol tersebut, pengujian berhasil dijalankan sesuai ekspektasi.
- Hal yang akhirnya jelas setelah praktik
Pada device saya, lokasi penempatan file JSON untuk proses write maupun read perlu didefinisikan lebih jelas. Contohnya:
src/main/resources/json/activity_id.json
- Dengan path yang eksplisit seperti ini, proses penyimpanan dan pembacaan file menjadi konsisten dan tidak menimbulkan error.
