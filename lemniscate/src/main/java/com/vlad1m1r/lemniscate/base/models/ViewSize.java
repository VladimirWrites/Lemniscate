/*
 * Copyright 2016 Vladimir Jovanovic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vlad1m1r.lemniscate.base.models;

public class ViewSize {

    private float size;
    private float sizeMultiplier = 1;

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getSizeMultiplier() {
        return sizeMultiplier;
    }

    public void setSizeMultiplier(float sizeMultiplier) {
        this.sizeMultiplier = sizeMultiplier;
    }
}
