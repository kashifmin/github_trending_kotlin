package me.kashifminhaj.githubtrending

import junit.framework.Assert.assertEquals
import me.kashifminhaj.githubtrending.extensions.toLastWeek
import me.kashifminhaj.githubtrending.extensions.toYesterday
import org.junit.Test
import java.util.*

/**
 * Created by kashif on 21/11/17.
 */
class ExtensionTests {
    @Test
    fun testDateToYesterday() {
        val now = Date(1511285883273)
        assertEquals(now.toYesterday().time , 1511199483273)
    }

    @Test
    fun testDateToLastWeek() {
        val now = Date(1511285883273)
        assertEquals(now.toLastWeek().time , 1510681083273)
    }
}