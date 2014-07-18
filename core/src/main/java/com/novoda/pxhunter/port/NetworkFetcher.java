/**
 * Copyright 2012 Novoda Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.novoda.pxhunter.port;

import java.io.File;
import java.io.InputStream;

/**
 * Responsible for fetching images from the network.
 */
public interface NetworkFetcher {

    /**
     * Retrieves image at the given url and stores it at the file specified
     *
     * Throws an ImageNotFound exception when the url could not be resolved.
     *
     * @param url URL of the image to be downloaded.
     * @param f   File where the image should be stored.
     */
    void retrieveImage(String url, File f);

    /**
     * Returns the input stream for the given url
     * Throws an ImageNotFound exception when the url could not be resolved.
     *
     * @param url URL of the image to be downloaded
     * @return input stream of the image or null on error.
     */
    InputStream retrieveInputStream(String url);

}
