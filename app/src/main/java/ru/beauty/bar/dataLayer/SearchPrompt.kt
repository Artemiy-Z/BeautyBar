package ru.beauty.bar.dataLayer

class SearchPrompt {
    var regex: Regex? = null
    var filterCategory: Int = -1
    var filterPriceMin: Int = -1
    var filterPriceMax: Int = Int.MAX_VALUE
}
