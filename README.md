# Veteriner Yönetim Sistemi API

Bu proje, bir veteriner kliniği yönetim sisteminin RESTful API'sini sunmaktadır.



![Ekran Görüntüsü (50)](https://github.com/cgezginci/vetManagementSystem/assets/143842154/ef074ba7-4a13-4e3b-988b-5dcd78e7b5ec)




## Customer (Müşteri) Tablosu

- `POST /v1/customer`: Yeni bir müşteri ekler.
- `GET /v1/customer/{name}`: Belirtilen isme sahip müşteriyi ve sahip olduğu hayvanları getirir.
- `DELETE /v1/customer/{id}`: Belirtilen ID'ye sahip müşteriyi siler.
- `PUT /v1/customer`: Belirtilen müşteriyi günceller.
- `GET /v1/customer/{name}/{animalName}`: Belirtilen müşteri ismi ve hayvan ismiyle eşleşen hayvanları getirir.

## Animal (Hayvan) Tablosu

- `POST /v1/animal`: Yeni bir hayvan ekler.
- `GET /v1/animal/{name}`: Belirtilen isme sahip hayvanı getirir.
- `DELETE /v1/animal/{id}`: Belirtilen ID'ye sahip hayvanı siler.
- `PUT /v1/animal`: Belirtilen hayvanları günceller.

## Vaccine (Aşı) Tablosu

- `POST /v1/vaccine`: Yeni bir aşı ekler.
- `DELETE /v1/vaccine/{id}`: Belirtilen ID'ye sahip aşıyı siler.
- `GET /v1/vaccine/{vaccineName}/{animalName}`: Belirtilen aşı ve hayvan ismine göre filtreleme yapar.
- `GET /v1/vaccine/filter/{animalName}`: Belirtilen hayvan adına göre bütün aşıları getirir.
- `GET /v1/vaccine/date`: Belirtilen koruyuculuk tarihine göre aşıları filtreler.
- `GET /v1/vaccine/{name}`: Belirtilen aşı ismine göre filtreleme yapar.
- `PUT /v1/vaccine`: Belirtilen aşıyı günceller.

## Doctor (Doktor) Tablosu

- `POST /v1/doctor`: Yeni bir doktor ekler.
- `GET /v1/doctor/{name}`: Belirtilen isme sahip doktoru ve müsait günlerini getirir.
- `DELETE /v1/doctor/{id}`: Belirtilen ID'ye sahip doktoru siler.
- `PUT /v1/doctor`: Belirtilen doktoru günceller.

## AvailableDate (Müsait Gün) Tablosu

- `POST /v1/available-date`: Yeni bir müsait tarih ekler.
- `GET /v1/available-date/{id}`: Belirtilen ID'ye sahip müsait tarihi getirir.
- `DELETE /v1/available-date/{id}`: Belirtilen ID'ye sahip müsait tarihi siler.
- `PUT /v1/available-date`: Belirtilen müsait tarihi günceller.

## Appointment (Randevu) Tablosu

- `POST /v1/appointment`: Yeni bir randevu ekler.
- `GET /v1/appointment/{id}`: Belirtilen ID'ye sahip randevuyu getirir.
- `DELETE /v1/appointment/{id}`: Belirtilen ID'ye sahip randevuyu siler.
- `GET /v1/appointment/filter`: Belirtilen hayvan adı ve tarih aralığına göre randevuları getirir.
- `GET /v1/appointment/filtered`: Belirtilen doktor adı ve tarih aralığına göre randevuları getirir.
- `PUT /v1/appointment`: Belirtilen randevuyu günceller.

## Genel Özellikler
* Aynı isme sahip bir aşı eklemek için aşının koruma gününün geçmiş olması gerekmektedir.
* Doktora randevu ekleyebilmek için o gün müsait olmalı ve o saatte başka bir randevusu olmamalıdır.


