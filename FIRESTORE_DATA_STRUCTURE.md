# War Kingdoms Pro - Firestore Veri Yapısı

## Genel Bakış

Bu dokümantasyon, War Kingdoms Pro oyununun Firestore veritabanı yapısını ve güvenlik kurallarını açıklar. Veri yapısı, orijinal APK'dan çıkarılan Java sınıflarının analizi sonucunda tasarlanmıştır.

## Koleksiyonlar

### 1. Users Collection (`/users/{userId}`)

**Açıklama:** Kullanıcı profil verileri ve oyun istatistikleri

**Veri Yapısı (ViewKingdomDto$Userdata tabanlı):**
```javascript
{
  user_id: string,
  achievement_set: string,
  age: string,
  age_point: string,
  created_colonies: string,
  email_confirmed: string,
  last_message: string,
  last_news: string,
  last_notification: string,
  last_task: string,
  loss: string,
  privacy: string,
  protection_mode: string,
  ref: string,
  task_completed: string,
  wiki_rating: string,
  win: string,
  created_at: timestamp,
  updated_at: timestamp
}
```

**Güvenlik:** Sadece kendi verilerine erişim

### 2. Kingdoms Collection (`/kingdoms/{kingdomId}`)

**Açıklama:** Krallık verileri, kaynaklar ve bina bilgileri

**Veri Yapısı (ViewKingdomDto$Kingdom ve KingdomInfoDto tabanlı):**
```javascript
{
  // Temel krallık bilgileri
  base_id: number,
  city: string,
  civ: string,
  country: string,
  email: string,
  gender: string,
  id: string,
  lastlogin: string,
  logincount: number,
  network: number,
  status: string,
  username: string,
  owner_id: string,
  
  // Yaş ve ilerleme
  age_count: number,
  age_name: string,
  age_name_next: string,
  age_point: string,
  current_age_no: string,
  next_age_no: string,
  new_age_available: boolean,
  
  // Kaynak üretimi
  alliance_gold_gain: string,
  center_food_consumption: string,
  center_food_gain: string,
  center_gold_gain: string,
  center_wood_gain: string,
  city_diamond_gain: string,
  colony_gold_gain: string,
  total_gold_gain: string,
  
  // Nüfus sayıları
  farmer_count_text: string,
  goldminer_count_text: string,
  woodcutter_count_text: string,
  total_villager_count_text: string,
  
  // Askeri birimler
  milunit_center_count_text: string,
  milunit_city_count_text: string,
  milunit_colony_count_text: string,
  total_milunit_count_text: string,
  
  // Casus sayıları
  spy_center_count_text: string,
  spy_city_count_text: string,
  spy_colony_count_text: string,
  total_spy_count_text: string,
  
  // Diğer
  resource_limits: object,
  badges: object,
  tutorial: array,
  alliance_members: array,
  created_at: timestamp,
  updated_at: timestamp
}
```

**Güvenlik:** Okuma herkese açık, yazma sadece sahibi ve ittifak üyelerine

### 3. Armies Collection (`/armies/{armyId}`)

**Açıklama:** Askeri birim verileri

**Veri Yapısı (BarrackDto$Unit tabanlı):**
```javascript
{
  _class: string,
  _start_level: number,
  age_point_multiplier: number,
  armor: number,
  army_count: number,
  cavalry_attack: number,
  cost: {
    food: number,
    wood: number,
    gold: number
  },
  count: number,
  importance: number,
  infantry_attack: number,
  information: string,
  level: number,
  maxlevel: number,
  pierce_armor: number,
  place_id: number,
  production_feature: string,
  owner_id: string,
  kingdom_id: string,
  created_at: timestamp,
  updated_at: timestamp
}
```

**Güvenlik:** Okuma herkese açık, yazma sadece sahibine

### 4. Technologies Collection (`/technologies/{techId}`)

**Açıklama:** Teknoloji araştırmaları ve akademi verileri

**Veri Yapısı (AcademyDto tabanlı):**
```javascript
{
  age: number,
  building_name: string,
  complete_now_cost: string,
  defence_bonus: number,
  description: string,
  max_prods: object,
  ongoing: {
    research_id: string,
    time_remaining: number,
    started_at: timestamp
  },
  ongoing_unit: object,
  time_left: number,
  unit_details: object,
  units: array,
  owner_id: string,
  kingdom_id: string,
  created_at: timestamp,
  updated_at: timestamp
}
```

**Güvenlik:** Okuma herkese açık, yazma sadece sahibine

### 5. Alliances Collection (`/alliances/{allianceId}`)

**Açıklama:** İttifak verileri ve üye yönetimi

**Veri Yapısı (AllianceDto tabanlı):**
```javascript
{
  alliance_leader: string, // user_id
  alliance_limit: number,
  alliance_member: number,
  alliance_rank: number,
  can_report: number,
  challengeGold: string,
  challengeWar: string,
  forum_unreads: number,
  invitations_count: number,
  member_count: number,
  newbie_member_count: number,
  owned_cities: object,
  request_already_sent: number,
  request_invite_sent: number,
  requests_count: number,
  suggested_member_count: number,
  members: array, // user_id listesi
  name: string,
  description: string,
  created_at: timestamp,
  updated_at: timestamp
}
```

**Güvenlik:** Okuma herkese açık, yazma lider ve üyelere

### 6. Markets Collection (`/markets/{marketId}`)

**Açıklama:** Pazar sistemi ve ticaret verileri

**Veri Yapısı (MarketDto tabanlı):**
```javascript
{
  age: number,
  building_name: string,
  capacity: string,
  complete_now_cost: string,
  defence_bonus: number,
  description: string,
  max_prods: object,
  ongoing: object,
  ongoing_unit: object,
  time_left: number,
  unit_details: object,
  owner_id: string,
  kingdom_id: string,
  trades: array,
  created_at: timestamp,
  updated_at: timestamp
}
```

**Güvenlik:** Okuma herkese açık, yazma sadece sahibine

### 7. Maps Collection (`/maps/{mapId}`)

**Açıklama:** Harita verileri ve konum bilgileri

**Veri Yapısı (MapDto tabanlı):**
```javascript
{
  alliance_count: number,
  base: object,
  campaign_coordinates: object,
  cartography: number,
  center_count: number,
  city_count: number,
  cl_lang: string,
  cn_lang: string,
  colony_count: number,
  coordinates: array,
  grid_time: number,
  map_data: array,
  created_at: timestamp,
  updated_at: timestamp
}
```

**Güvenlik:** Okuma herkese açık, yazma sadece adminlere

### 8. Battles Collection (`/battles/{battleId}`)

**Açıklama:** Savaş raporları ve sonuçları

**Veri Yapısı (LogsMilitaryWarReportDto tabanlı):**
```javascript
{
  attacker_casualties: array,
  attacker_name: string,
  attacker_id: string,
  attacker_ratio: number,
  defender_casualties: object,
  defender_ratio: number,
  defender_id: string,
  defenders_list: object,
  participants: array, // tüm katılımcı user_id'leri
  battle_result: string, // "victory", "defeat", "draw"
  battle_type: string, // "raid", "siege", "defense"
  location: {
    x: number,
    y: number
  },
  started_at: timestamp,
  ended_at: timestamp,
  created_at: timestamp
}
```

**Güvenlik:** Sadece katılımcılar okuyabilir ve yazabilir

### 9. Panels Collection (`/panels/{panelId}`)

**Açıklama:** Oyun paneli, görevler ve bildirimler

**Veri Yapısı (PanelDto tabanlı):**
```javascript
{
  age: string,
  age_point: string,
  allianceInvitation: object,
  alliance_id: string,
  animDisplay: number,
  api_last_action: array,
  armageddon: object,
  tasks: array,
  tutorials: array,
  guides: array,
  challenges: array,
  building_processes: array,
  shop_types: array,
  rewards: array,
  campaigns: array,
  user_id: string,
  created_at: timestamp,
  updated_at: timestamp
}
```

**Güvenlik:** Sadece kendi verilerine erişim

### 10. Chat Messages Collection (`/chat_messages/{messageId}`)

**Açıklama:** Sohbet mesajları

**Veri Yapısı:**
```javascript
{
  sender_id: string,
  sender_name: string,
  message: string,
  channel: string, // "global", "alliance", "private"
  recipient_id: string, // private mesajlar için
  alliance_id: string, // ittifak mesajları için
  timestamp: timestamp,
  edited: boolean,
  edited_at: timestamp
}
```

**Güvenlik:** Okuma herkese açık, yazma sadece gönderene, moderasyon yetkisi

### 11. Game Sessions Collection (`/game_sessions/{sessionId}`)

**Açıklama:** Oyun oturumu verileri

**Veri Yapısı:**
```javascript
{
  user_id: string,
  kingdom_id: string,
  session_start: timestamp,
  session_end: timestamp,
  last_activity: timestamp,
  device_info: string,
  ip_address: string,
  actions_count: number
}
```

**Güvenlik:** Sadece kendi oturumlarına erişim

## Güvenlik Kuralları Özeti

1. **Kimlik Doğrulama:** Tüm işlemler için `request.auth != null` gerekli
2. **Veri Sahipliği:** Kullanıcılar sadece kendi verilerini değiştirebilir
3. **İttifak Yetkileri:** İttifak üyeleri belirli verilere erişebilir
4. **Admin Yetkileri:** Harita verileri gibi kritik veriler sadece adminler tarafından değiştirilebilir
5. **Sohbet Moderasyonu:** Moderatörler mesajları düzenleyebilir/silebilir
6. **Savaş Katılımı:** Savaş raporlarına sadece katılımcılar erişebilir

## Kullanım Önerileri

1. **İndeksleme:** Sık sorgulanan alanlar için Firestore indeksleri oluşturun
2. **Batch İşlemler:** Çoklu veri güncellemeleri için batch operations kullanın
3. **Real-time Updates:** Sohbet ve savaş verileri için real-time listeners kullanın
4. **Veri Validasyonu:** Client-side ve server-side validasyon uygulayın
5. **Rate Limiting:** API çağrıları için rate limiting uygulayın

## Geliştirme Notları

- Bu veri yapısı, orijinal APK'dan çıkarılan Java sınıflarının analizi sonucunda tasarlanmıştır
- Gerçek implementasyonda, oyunun ihtiyaçlarına göre ek alanlar eklenebilir
- Performans optimizasyonu için denormalizasyon gerekebilir
- Büyük veri setleri için pagination implementasyonu önerilir