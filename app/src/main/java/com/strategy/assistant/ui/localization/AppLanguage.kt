package com.strategy.assistant.ui.localization

import androidx.compose.runtime.compositionLocalOf

val LocalAppLanguage = compositionLocalOf { "English" }

fun appText(key: String): String {
    val arabic = LocalAppLanguage.current == "العربية"
    val translations = mapOf(
        "home" to "الرئيسية",
        "ai" to "الذكاء",
        "games" to "الألعاب",
        "more" to "المزيد",
        "welcome" to "أهلاً بعودتك",
        "command_ready" to "مركز القيادة جاهز.",
        "ai_assistant" to "المساعد الذكي",
        "ai_description" to "احصل على استراتيجيات وتحليلات وخطط ذكية للألعاب.",
        "games_label" to "الألعاب",
        "accounts" to "الحسابات",
        "activities" to "الأنشطة",
        "quick_access" to "الوصول السريع",
        "explore" to "استكشاف",
        "ai_center" to "مركز الذكاء الاصطناعي",
        "game_library" to "مكتبة الألعاب",
        "activity" to "النشاط",
        "management" to "الإدارة",
        "recent_activity" to "النشاط الأخير",
        "intelligence_ready" to "نواة الذكاء جاهزة",
        "no_games" to "لم تتم إضافة ألعاب بعد",
        "now" to "الآن",
        "new" to "جديد",
        "settings" to "الإعدادات",
        "language" to "اللغة",
        "choose_language" to "اختر لغة التطبيق",
        "system_configuration" to "إعدادات النظام",
        "general" to "عام",
        "notifications" to "الإشعارات",
        "privacy_data" to "الخصوصية والبيانات",
        "ai_preferences" to "تفضيلات الذكاء الاصطناعي",
        "push_notifications" to "الإشعارات",
        "sound_effects" to "المؤثرات الصوتية",
        "vibration" to "الاهتزاز",
        "anonymous_analytics" to "إحصاءات مجهولة",
        "appearance" to "المظهر",
        "date_time" to "التاريخ والوقت",
        "dark_mode" to "الوضع الداكن",
        "system_default" to "إعدادات النظام",
        "manage" to "إدارة",
        "view" to "عرض",
        "configure" to "ضبط",
        "choose_first_game" to "اختر لعبتك الأولى عندما تكون مستعداً.",
        "your_collection" to "مجموعتك",
        "back" to "رجوع"
    )
    val english = mapOf(
        "home" to "Home", "ai" to "AI", "games" to "Games", "more" to "More",
        "welcome" to "WELCOME BACK", "command_ready" to "Your command center is ready.",
        "ai_assistant" to "AI ASSISTANT", "ai_description" to "Get game strategies, analysis and smart plans.",
        "games_label" to "GAMES", "accounts" to "ACCOUNTS", "activities" to "ACTIVITIES",
        "quick_access" to "QUICK ACCESS", "explore" to "Explore", "ai_center" to "AI Center",
        "game_library" to "Game Library", "activity" to "Activity", "management" to "Management",
        "recent_activity" to "RECENT ACTIVITY", "intelligence_ready" to "Intelligence core is ready",
        "no_games" to "No games added yet", "now" to "NOW", "new" to "NEW",
        "settings" to "Settings", "language" to "Language", "choose_language" to "Choose your application language",
        "system_configuration" to "SYSTEM CONFIGURATION", "general" to "GENERAL",
        "notifications" to "NOTIFICATIONS", "privacy_data" to "PRIVACY & DATA",
        "ai_preferences" to "AI PREFERENCES", "push_notifications" to "Push Notifications",
        "sound_effects" to "Sound Effects", "vibration" to "Vibration",
        "anonymous_analytics" to "Anonymous Analytics", "appearance" to "Appearance",
        "date_time" to "Date & Time", "dark_mode" to "Dark Mode", "system_default" to "System Default",
        "manage" to "Manage", "view" to "View", "configure" to "Configure",
        "choose_first_game" to "Choose your first game when you're ready.",
        "your_collection" to "Your Collection", "back" to "Back"
    )
    return if (arabic) translations[key] ?: english[key] ?: key else english[key] ?: key
}
