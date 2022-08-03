package com.example.countrieschallange.cons

class NullResponseException (message: String = "Response is null") : Exception(message)
class ResponseIsFailure (message: String = "Response is failure") : Exception(message)