package com.medha.myapplication.api

abstract class BaseParserClass {

    abstract fun parser(responseBody: Any?, callHistory:ArrayList<Any>? = null):Any?
}