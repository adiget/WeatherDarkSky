package com.ags.annada.weather

import android.app.Fragment
import android.app.FragmentManager
import android.os.Bundle
import android.util.Log
import java.lang.ref.WeakReference
import java.util.HashMap

/**
 * Created by : annada
 * Date : 24/02/2018.
 */

class StateMaintainer
/**
 * Constructor
 */
(fragmentManager: FragmentManager, private val mStateMaintenerTag: String) {
    protected val TAG = javaClass.simpleName
    private val mFragmentManager: WeakReference<FragmentManager>
    private var mStateMaintainerFrag: StateMngFragment? = null
    private var mIsRecreating: Boolean = false

    init {
        mFragmentManager = WeakReference(fragmentManager)
    }

    /**
     * Creates the Fragment responsible to maintain the objects.
     * @return  true: fragment just created
     */
    fun firstTimeIn(): Boolean {
        try {
            // Retrieving reference
            mStateMaintainerFrag = mFragmentManager.get()?.findFragmentByTag(mStateMaintenerTag) as? StateMngFragment

            // Creating new RetainedFragment
            if (mStateMaintainerFrag == null) {
                Log.d(TAG, "creating new RetainedFragment " + mStateMaintenerTag)
                mStateMaintainerFrag = StateMngFragment()
                mFragmentManager.get()!!.beginTransaction()
                        .add(mStateMaintainerFrag, mStateMaintenerTag).commit()
                mIsRecreating = false
                return true
            } else {
                Log.d(TAG, "Returning retained fragment existing " + mStateMaintenerTag)
                mIsRecreating = true
                return false
            }
        } catch (e: NullPointerException) {
            Log.w(TAG, "Erro firstTimeIn()")
            return false
        }

    }

    /**
     * Return **true** it the current Activity was recreated at least one time
     * @return  If the Activity was recreated
     */
    fun wasRecreated(): Boolean {
        return mIsRecreating
    }


    /**
     * Insert the object to be preserved.
     * @param key   object's TAG
     * @param obj   object to maintain
     */
    fun put(key: String, obj: Any) {
        mStateMaintainerFrag!!.put(key, obj)
    }

    /**
     * Insert the object to be preserved.
     * @param obj   object to maintain
     */
    fun put(obj: Any) {
        put(obj.javaClass.name, obj)
    }


    /**
     * Recovers the object saved
     * @param key   Object's TAG
     * @param <T>   Object type
     * @return      Object saved
    </T> */
    operator fun <T> get(key: String): T? {
        return mStateMaintainerFrag!!.get<T>(key)

    }

    /**
     * Checks the existence of a given object
     * @param key   Key to verification
     * @return      true: Object exists
     */
    fun hasKey(key: String): Boolean {
        return mStateMaintainerFrag!!.get<Any>(key) != null
    }


    /**
     * Fragment resposible to preserve objects.
     * Instantiated only once. Uses a hashmap to save objs
     */
    class StateMngFragment : Fragment() {
        private val mData = HashMap<String, Any>()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            // Grants that the fragment will be preserved
            retainInstance = true
        }

        /**
         * Insert objects on the hashmap
         * @param key   Reference key
         * @param obj   Object to be saved
         */
        fun put(key: String, obj: Any) {
            mData[key] = obj
        }

        /**
         * Insert objects on the hashmap
         * @param object    Object to be saved
         */
        fun put(`object`: Any) {
            put(`object`.javaClass.name, `object`)
        }

        /**
         * Recovers saved object
         * @param key   Reference key
         * @param <T>   Object type
         * @return      Object saved
        </T> */
        operator fun <T> get(key: String): T? {
            return mData[key] as T
        }
    }
}