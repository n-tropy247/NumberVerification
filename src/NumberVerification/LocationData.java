/* 
 * Copyright (C) 2018 Ryan Castelli
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package NumberVerification;

/**
 * AreaCode object to store connections between area code, city, and state
 *
 * @author: N-Tropy
 * @version: 1.0
 */
public class LocationData {

    private final int CODE;
    private final String CITY;
    private final String STATE;

    /**
     *
     * @param aCode area code from phone number
     * @param aCity name of city
     * @param aState name of state
     */
    public LocationData(int aCode, String aCity, String aState) {
        this.CODE = aCode;
        this.CITY = aCity;
        this.STATE = aState;
    }

    public int getCode() {
        return CODE;
    }

    public String getCity() {
        return CITY;
    }

    public String getState() {
        return STATE;
    }
}
