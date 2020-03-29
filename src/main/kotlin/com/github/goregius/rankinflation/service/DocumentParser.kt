package com.github.goregius.rankinflation.service

interface DocumentParser<R> {
    fun parse(html: String): R
}