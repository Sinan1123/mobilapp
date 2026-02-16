# Nefes Alarm (Android Studio Şablonu)

Bu proje, Android Studio'da **direkt açıp geliştirebileceğin** tam bir başlangıç şablonudur.

## Özellikler
- Her gün **07:00**, **12:00**, **17:00** saatlerinde alarm tetikler.
- Uygulama kapalı olsa bile alarm sesi çalar (Foreground Service).
- Alarm anında opak siyah arkaplanlı ekranda:
  - "Nefes egzersizine geçelim mi?"
  - `444` ve `478` butonları görünür.
- Seçilen butona göre ilgili video oynatılır.
- Telefon yeniden başlatılınca alarmlar otomatik yeniden planlanır.

## Klasör Şablonu

```text
mobilapp/
├─ build.gradle.kts
├─ settings.gradle.kts
├─ gradle.properties
└─ app/
   ├─ build.gradle.kts
   └─ src/main/
      ├─ AndroidManifest.xml
      ├─ java/com/mobilapp/
      │  ├─ AlarmConstants.kt
      │  ├─ AlarmScheduler.kt
      │  ├─ AlarmReceiver.kt
      │  ├─ AlarmAudioService.kt
      │  ├─ BootReceiver.kt
      │  ├─ PromptActivity.kt
      │  └─ MainActivity.kt
      └─ res/
         ├─ layout/
         │  ├─ activity_main.xml
         │  └─ activity_prompt.xml
         ├─ values/
         │  ├─ strings.xml
         │  └─ themes.xml
         └─ raw/
            ├─ morning_alarm.mp3
            ├─ noon_alarm.mp3
            ├─ evening_alarm.mp3
            ├─ breath_444.mp4
            └─ breath_478.mp4
```

## Medya Dosyaları
`app/src/main/res/raw` içindeki dosyalar şu an placeholder'dır. Gerçek dosyalarını aynı isimlerle değiştir:
- `morning_alarm.mp3`
- `noon_alarm.mp3`
- `evening_alarm.mp3`
- `breath_444.mp4`
- `breath_478.mp4`

## Android Studio'da Çalıştırma
1. Projeyi Android Studio ile aç.
2. Gradle sync tamamla.
3. Fiziksel cihazda test et (alarm/exact alarm davranışı için önerilir).
4. Uygulamayı açınca gerekli izinleri ver.

## Not
Android 12+ cihazlarda exact alarm izni için sistem ekranına yönlendirme yapılır.
