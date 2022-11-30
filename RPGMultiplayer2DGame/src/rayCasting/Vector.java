package rayCasting;

public class Vector {

   double dX;
   double dY;

   public Vector() {
      dX = dY = 0.0;
   }

   public Vector( double dX, double dY ) {
      this.dX = dX;
      this.dY = dY;
   }
   
   public Vector normalize() {
      Vector v2 = new Vector();

      double length = Math.sqrt( this.dX*this.dX + this.dY*this.dY );
      if (length != 0) {
        v2.dX = this.dX/length;
        v2.dY = this.dY/length;
      }

      return v2;
   }  
}