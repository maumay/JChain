/**
 *
 */
package xawd.jflow.geometry;

import xawd.jflow.Flow;
import xawd.jflow.geometry.splines.LineSegment;

/**
 * @author t
 *
 */
public interface Polygon
{
	Flow<Point> points();

	Flow<LineSegment> lines();
}
