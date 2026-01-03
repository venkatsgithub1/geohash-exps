package org.example.utils;

public class CommonUtils {
    public static String getEstimatedH3Distance(int k, int resolution) {
        switch(resolution) {
            case 9:
                switch(k) {
                    case 1: return "in 302 meters";
                    case 2: return "in 604 meters";
                    case 3: return "in 906 meters";
                    case 4: return "in 1208 meters";
                    case 5: return "in 1510 meters";
                    case 6: return "in 1812 meters";
                    case 7: return "in 2114 meters";
                    default: return "> 2 kms";
                }
            default:
                return "Not supported yet";
        }
    }
}
