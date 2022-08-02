package com.example.countrieschallange.cons

class NullResponseException (message: String = "Response is null") : Exception(message)
class ResponseIsFailure (message: String = "404: not found. Contact support team") : Exception(message)