package com.vlad1m1r.lemniscate.testutils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class TestLayoutInflater internal constructor(context: Context) : LayoutInflater(context) {
    internal var resId: Int = 0
        private set
    internal var root: ViewGroup? = null
        private set

    override fun inflate(resource: Int, root: ViewGroup?): View? {
        this.resId = resource
        this.root = root
        return null
    }

    override fun inflate(resource: Int, root: ViewGroup?, attachToRoot: Boolean): View? {
        this.resId = resource
        this.root = root
        return null
    }

    override fun cloneInContext(newContext: Context): LayoutInflater? {
        return null
    }
}
