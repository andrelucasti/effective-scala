package OO

import java.util.UUID

class Geolocation(latitude: Double, longitude: Double)

enum DeliveryRegion(geolocation: Geolocation) {
  case LISBON extends DeliveryRegion(new Geolocation(1,1))
  case PORTO extends DeliveryRegion(new Geolocation(0,0))
}

enum VehicleType {
  case CAR, BICYCLE, MOTORCYCLE
}

class Driver(uuid: UUID, name: String, deliveryRegion: DeliveryRegion, vehicleType: VehicleType){
  def this(name: String, deliveryRegion: DeliveryRegion, vehicleType: VehicleType) =
    this(UUID.randomUUID(), name, deliveryRegion, vehicleType)
}