package com.huberthe.photobrowser.data

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import junit.framework.Assert.fail
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class GalleryRepositoryTest {

    private val photoService: PhotoService = mock()

    private val repository: GalleryRepository = GalleryRepository(photoService)

    @Test
    fun test_getPhotos() {
        val jsonParser = JsonParser()
        val jsonElement = jsonParser.parse(RESPONSE)
        whenever(photoService.getPhotos(any(), any(), any(), any(), any())).thenReturn(Observable.just(jsonElement))

        val countDownLatch = CountDownLatch(1)
        val size = 100
        repository.retrievePhotos(size, size, 1, 10)
            .subscribe({
                println(it.size)
                Assert.assertEquals(3, it.size)
                countDownLatch.countDown()
            }, {
                it.printStackTrace()
                fail()
            })
        assert(countDownLatch.await(2, TimeUnit.SECONDS))

        verify(photoService).getPhotos(eq(size), eq(size), eq(1), eq(10), eq("8630898-e092bf16cb1dd9ff6a483dabf"))
    }

    companion object {
        private const val RESPONSE =
            "{\"totalHits\":500,\"hits\":[{\"largeImageURL\":\"https://pixabay.com/get/ed35b30f2bf0083ed1584d05fb1d4e90e077eadc11ac104490f1c571a5e8b2b1_1280.jpg\",\"webformatHeight\":544,\"webformatWidth\":640,\"likes\":104,\"imageWidth\":5184,\"id\":4020349,\"user_id\":2364555,\"views\":17624,\"comments\":22,\"pageURL\":\"https://pixabay.com/photos/brownie-cake-greeting-card-pastries-4020349/\",\"imageHeight\":4410,\"webformatURL\":\"https://pixabay.com/get/ed35b30f2bf0083ed1584d05fb1d4e90e077eadc11ac104490f1c571a5e8b2b1_640.jpg\",\"type\":\"photo\",\"previewHeight\":127,\"tags\":\"brownie, cake, greeting card\",\"downloads\":15939,\"user\":\"pixel2013\",\"favorites\":65,\"imageSize\":1693160,\"previewWidth\":150,\"userImageURL\":\"https://cdn.pixabay.com/user/2019/01/30/19-38-32-471_250x250.jpg\",\"previewURL\":\"https://cdn.pixabay.com/photo/2019/02/25/19/27/brownie-4020349_150.jpg\"},{\"largeImageURL\":\"https://pixabay.com/get/ed35b30f2bf0033ed1584d05fb1d4e90e077eadc11ac104490f1c571a5e8b2b1_1280.jpg\",\"webformatHeight\":426,\"webformatWidth\":640,\"likes\":92,\"imageWidth\":5184,\"id\":4020342,\"user_id\":2364555,\"views\":13766,\"comments\":16,\"pageURL\":\"https://pixabay.com/photos/brownie-cake-greeting-card-pastries-4020342/\",\"imageHeight\":3451,\"webformatURL\":\"https://pixabay.com/get/ed35b30f2bf0033ed1584d05fb1d4e90e077eadc11ac104490f1c571a5e8b2b1_640.jpg\",\"type\":\"photo\",\"previewHeight\":99,\"tags\":\"brownie, cake, greeting card\",\"downloads\":12767,\"user\":\"pixel2013\",\"favorites\":65,\"imageSize\":996543,\"previewWidth\":150,\"userImageURL\":\"https://cdn.pixabay.com/user/2019/01/30/19-38-32-471_250x250.jpg\",\"previewURL\":\"https://cdn.pixabay.com/photo/2019/02/25/19/22/brownie-4020342_150.jpg\"},{\"largeImageURL\":\"https://pixabay.com/get/ed35b30d2cf6063ed1584d05fb1d4e90e077eadc11ac104490f1c571a5e8b2b1_1280.jpg\",\"webformatHeight\":426,\"webformatWidth\":640,\"likes\":65,\"imageWidth\":7087,\"id\":4022427,\"user_id\":3764790,\"views\":10788,\"comments\":45,\"pageURL\":\"https://pixabay.com/photos/spring-landscape-flowers-rainbow-4022427/\",\"imageHeight\":4724,\"webformatURL\":\"https://pixabay.com/get/ed35b30d2cf6063ed1584d05fb1d4e90e077eadc11ac104490f1c571a5e8b2b1_640.jpg\",\"type\":\"photo\",\"previewHeight\":99,\"tags\":\"spring, landscape, flowers\",\"downloads\":9172,\"user\":\"enriquelopezgarre\",\"favorites\":33,\"imageSize\":9611132,\"previewWidth\":150,\"userImageURL\":\"https://cdn.pixabay.com/user/2019/01/28/18-34-24-746_250x250.jpg\",\"previewURL\":\"https://cdn.pixabay.com/photo/2019/02/26/16/10/spring-4022427_150.jpg\"}],\"total\":929379}"
    }
}