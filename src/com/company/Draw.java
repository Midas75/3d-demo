package com.company;

public class Draw {

    public static void drawBuffer(Buffer buffer, Light[] lightSet, Camera camera, VisualWindow visualwindow, boolean flush) {
        buffer.interpolationBuffer(camera);
        for (int i = buffer.minX; i < buffer.maxX; i++) {
            for (int j = buffer.minY; j < buffer.maxY; j++) {
                Buffer.BUnit bUnit = buffer.bUnitSet[i][j];
                if (bUnit != null && bUnit.transformedTri != null && (camera.obMode & Camera.SHOW_LIGHT) != 0) {
                    {
                        Light.placeLightToBuffer(buffer,i,j, lightSet, camera);
                    }
                    visualwindow.putPixel(i, j, (int) bUnit.color.x, (int) bUnit.color.y, (int) bUnit.color.z);
                }
            }
        }
        if (flush) {
            visualwindow.flush();
        }
    }

    public static void drawAll(Camera camera, Geometry geometry, Light[] lightSet, VisualWindow visualwindow, boolean flush) {
        Buffer buffer = new Buffer(1024, 768, geometry.triangleSet);

        buffer.setBuffer(camera);

        drawBuffer(buffer, lightSet, camera, visualwindow, flush);
    }


}
