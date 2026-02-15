# Nefes Alarm (Android)

Bu proje, her gün saat **07:00**, **12:00** ve **17:00**'de (uygulama kapalı olsa bile) farklı bir alarm sesi çalacak şekilde hazırlanmıştır.

## Davranış
- 07:00 → `morning_alarm.mp3`
- 12:00 → `noon_alarm.mp3`
- 17:00 → `evening_alarm.mp3`
- Alarm tetiklendiğinde siyah opak arka planlı bir ekran açılır: **"Nefes egzersizine geçelim mi?"**
- İki buton vardır:
  - `444` → `breath_444.mp4`
  - `478` → `breath_478.mp4`
- Buton seçilince alarm sesi durur ve seçilen video oynatılır.

## Önemli Not
`app/src/main/res/raw` altındaki medya dosyaları şu anda **placeholder** olarak eklidir. Gerçek `.mp3` ve `.mp4` dosyalarınızı aynı isimlerle değiştirmeniz gerekir.

## Teknik Notlar
- Alarm planlaması: `AlarmManager#setExactAndAllowWhileIdle`
- Yeniden başlatma sonrası planlama: `BOOT_COMPLETED` receiver
- Ses çalma: foreground service (`AlarmAudioService`)
- UI prompt: `PromptActivity`
- Video oynatma: `MainActivity` + `VideoView`
