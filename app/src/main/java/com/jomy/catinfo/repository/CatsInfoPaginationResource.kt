package com.jomy.catinfo.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.jomy.catinfo.model.CatInfo
import java.io.IOException
import javax.inject.Inject

/**
 * PagingSource class to load pager with catinfo with subsequent page calls
 */
class CatsInfoPaginationResource @Inject
constructor(private val catsInfoRepository: NetworkCatsInfoRepository):PagingSource<Int,CatInfo>(){

    private val pageSize = 20
    override fun getRefreshKey(state: PagingState<Int, CatInfo>): Int?{
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatInfo> {
       return try{
            val nextPage = params.key?:0
            val catsInfo:List<CatInfo> = catsInfoRepository.getCatsInfo(pageSize,nextPage)
           LoadResult.Page(data = catsInfo,
                prevKey = if(nextPage ==0) null else nextPage - 1,
                nextKey = nextPage.plus(1))


        }catch (e: IOException) {
           LoadResult.Error(e)
       } catch (e: HttpException) {
           LoadResult.Error(e)
       }
       catch (e:Exception){
             LoadResult.Error(e)
        }
    }


}