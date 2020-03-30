package com.github.goregius.rankinflation.service

interface DocumentParser<R, T> {
    fun parse(html: String, data: T): R
}