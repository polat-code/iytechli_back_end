package com.example.iytechli.compliment.domain.model.entity;


public enum ReportReason {
    INAPPROPRIATE("Uygunsuz İçerik"),
    HARASSMENT("Taciz veya Zorbalık"),
    HATE_SPEECH("Nefret Söylemi"),
    MISINFORMATION("Yanıltıcı veya Yanlış Bilgi"),
    COPYRIGHT("Telif Hakkı İhlali"),
    PRIVACY("Özel Bilgilerin Paylaşılması"),
    SPAM("Spam veya Reklam"),
    HEALTH_MISINFORMATION("Sağlıkla İlgili Yanlış Bilgilendirme"),
    THREAT("Başkalarını Tehdit Etme"),
    DRUGS("Uyuşturucu veya İllegal Madde Kullanımı");

    private final String description;

    ReportReason(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
