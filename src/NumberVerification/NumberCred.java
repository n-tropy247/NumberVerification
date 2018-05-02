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

import java.io.FileReader;
import java.io.IOException;

import openCSV.CSVReader;

import java.util.List;

/**
 * Will read US area codes from CSV
 *
 * @author: N-Tropy
 * @version: 1.0
 */
public class NumberCred {

    private List<String[]> list;

    private String[][] dataArr;

    public NumberCred() {
        String importName = "us-area-code-cities.csv";

        try {
            CSVReader reader = new CSVReader(new FileReader(importName));

            list = reader.readAll();

            dataArr = new String[list.size()][];
            dataArr = list.toArray(dataArr);

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public int getArrSize() {
        return list.size();
    }

    public LocationData createLocations(int j) throws IOException {

        LocationData areaCode = new LocationData(Integer.parseInt(dataArr[j][0]), dataArr[j][1], dataArr[j][2]);

        return areaCode;
    }
}
