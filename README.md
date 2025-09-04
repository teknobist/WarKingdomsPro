# War Kingdoms Pro - Strategy Game Clone

## 📱 Proje Hakkında

Bu proje, orijinal "War Kingdoms Strategy Game" APK dosyasından klonlanmış ve profesyonel bir Android uygulama yapısına dönüştürülmüştür. Proje, özgünleştirme ve geliştirme için hazır hale getirilmiştir.

## 🏗️ Proje Yapısı

```
WarKingdomsPro/
├── app/
│   ├── src/main/
│   │   ├── java/com/warkingdoms/
│   │   │   ├── WarKingdomsApplication.kt          # Ana uygulama sınıfı
│   │   │   ├── ui/                               # Kullanıcı arayüzü
│   │   │   │   ├── MainActivity.kt               # Ana aktivite
│   │   │   │   ├── GameActivity.kt               # Oyun aktivitesi
│   │   │   │   └── SettingsActivity.kt           # Ayarlar aktivitesi
│   │   │   ├── core/utils/                       # Temel yardımcı sınıflar
│   │   │   │   ├── PreferenceUtil.kt             # Güvenli veri saklama
│   │   │   │   ├── MediaService.kt               # Ses ve müzik yönetimi
│   │   │   │   └── LanguageUtil.kt               # Dil yönetimi
│   │   │   ├── game/                             # Oyun motoru
│   │   │   │   └── GameEngine.kt                 # Ana oyun motoru
│   │   │   └── kingdom/strategy/war/             # Orijinal oyun kodları
│   │   │       ├── activities/                   # Aktiviteler
│   │   │       ├── adapters/                     # Liste adaptörleri
│   │   │       ├── customViews/                  # Özel görünümler
│   │   │       ├── db/                           # Veritabanı
│   │   │       ├── dtos/                         # Veri transfer objeleri
│   │   │       ├── enums/                        # Sabitler
│   │   │       ├── services/                     # Servisler
│   │   │       └── utils/                        # Yardımcı sınıflar
│   │   ├── res/                                  # Kaynaklar
│   │   │   ├── drawable/                         # Çizimler (75 dosya)
│   │   │   ├── drawable-hdpi/                    # HD çizimler (118 dosya)
│   │   │   ├── drawable-xhdpi/                   # XHD çizimler (242 dosya)
│   │   │   ├── layout/                           # Düzenler (235 dosya)
│   │   │   └── values/                           # Değerler (10 dosya)
│   │   ├── assets/                               # Varlıklar (8 dosya)
│   │   └── AndroidManifest.xml                   # Uygulama manifestosu
│   └── build.gradle                              # Uygulama build yapılandırması
├── gradle/wrapper/                               # Gradle wrapper
├── build.gradle                                  # Proje build yapılandırması
├── settings.gradle                               # Proje ayarları
├── gradle.properties                             # Gradle özellikleri
└── gradlew.bat                                   # Gradle wrapper (Windows)
```

## 🚀 Teknolojiler

### **Ana Teknolojiler**
- **Android SDK 34** - En güncel Android geliştirme kiti
- **Kotlin** - Modern Android geliştirme dili
- **Gradle 8.7** - Build sistemi

### **UI & UX**
- **View Binding** - Güvenli görünüm bağlama
- **Data Binding** - İki yönlü veri bağlama
- **Material Design** - Modern UI tasarımı
- **ConstraintLayout** - Esnek düzen sistemi

### **Ağ & Veri**
- **Retrofit 2** - REST API istemcisi
- **OkHttp 4** - HTTP istemcisi
- **Volley** - Ağ kütüphanesi
- **Gson** - JSON işleme

### **Firebase & Google Servisleri**
- **Firebase Analytics** - Kullanıcı analitikleri
- **Firebase Messaging** - Push bildirimleri
- **Firebase Crashlytics** - Hata raporlama
- **Google Play Services** - Google servisleri

### **Görsel & Medya**
- **Glide** - Görsel yükleme ve önbellekleme
- **Lottie** - Animasyonlar
- **ExoPlayer** - Medya oynatıcı

### **Dependency Injection**
- **Dagger 2** - Bağımlılık enjeksiyonu

### **Güvenlik**
- **Encrypted SharedPreferences** - Güvenli veri saklama
- **ProGuard** - Kod gizleme

## 🛠️ Kurulum

### Gereksinimler
- Android Studio Arctic Fox veya üzeri
- JDK 11 veya üzeri
- Android SDK 21+ (minimum)
- Android SDK 34 (hedef)

### Kurulum Adımları

1. **Projeyi Android Studio'da açın**
   ```bash
   File > Open > WarKingdomsPro klasörünü seçin
   ```

2. **Gradle Sync yapın**
   ```bash
   Tools > Android > Sync Project with Gradle Files
   ```

3. **Bağımlılıkları indirin**
   ```bash
   ./gradlew build
   ```

4. **Uygulamayı çalıştırın**
   ```bash
   Run > Run 'app'
   ```

## 🎮 Özellikler

### **Mevcut Özellikler**
- ✅ Profesyonel proje yapısı
- ✅ Modern Android mimarisi
- ✅ Güvenli veri saklama
- ✅ Çoklu dil desteği
- ✅ Ses ve müzik yönetimi
- ✅ Oyun motoru altyapısı
- ✅ Orijinal oyun kodları entegrasyonu

### **Özgünleştirme İçin Hazır**
- 🔧 UI/UX tasarımı değiştirilebilir
- 🔧 Oyun mekaniği özelleştirilebilir
- 🔧 Yeni özellikler eklenebilir
- 🔧 Grafik ve sesler değiştirilebilir
- 🔧 Ağ protokolleri özelleştirilebilir

## 📝 Özgünleştirme Rehberi

### **1. UI Değişiklikleri**
- `res/layout/` klasöründeki XML dosyalarını düzenleyin
- `res/values/colors.xml` ile renk paletini değiştirin
- `res/drawable/` klasöründeki görselleri değiştirin

### **2. Oyun Mekaniği**
- `GameEngine.kt` dosyasını özelleştirin
- `kingdom/strategy/war/` klasöründeki kodları düzenleyin
- Yeni özellikler için `game/` klasörüne eklemeler yapın

### **3. Ses ve Müzik**
- `MediaService.kt` ile ses yönetimini özelleştirin
- `res/raw/` klasörüne yeni ses dosyaları ekleyin

### **4. Dil Desteği**
- `LanguageUtil.kt` ile yeni diller ekleyin
- `res/values-[dil]/` klasörleri oluşturun

## 🔧 Build Yapılandırması

### **Debug Build**
```bash
./gradlew assembleDebug
```

### **Release Build**
```bash
./gradlew assembleRelease
```

### **APK İmzalama**
1. `app/build.gradle` dosyasında signing config ayarlayın
2. Keystore dosyası oluşturun
3. Release build alın

## 📊 Proje İstatistikleri

- **Toplam Dosya Sayısı**: 2,000+
- **Kaynak Dosyaları**: 1,759
- **Java/Kotlin Dosyaları**: 916+
- **Layout Dosyaları**: 235
- **Drawable Dosyaları**: 435
- **Asset Dosyaları**: 8

## 🤝 Katkıda Bulunma

1. Projeyi fork edin
2. Feature branch oluşturun (`git checkout -b feature/AmazingFeature`)
3. Değişikliklerinizi commit edin (`git commit -m 'Add some AmazingFeature'`)
4. Branch'inizi push edin (`git push origin feature/AmazingFeature`)
5. Pull Request oluşturun

## 📄 Lisans

Bu proje eğitim amaçlı oluşturulmuştur. Orijinal oyunun telif hakları ilgili sahiplerine aittir.

## 🆘 Destek

Sorularınız için:
- GitHub Issues kullanın
- Dokümantasyonu inceleyin
- Kod yorumlarını okuyun

---

**Not**: Bu proje özgünleştirme için hazır hale getirilmiştir. Geliştirme sürecinde orijinal oyunun telif haklarına saygı gösterilmelidir.