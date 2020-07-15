echo "==================="
echo "Building Application"
echo "==================="

echo "Removing images"

docker rmi $(docker images)


pd="/Users/giri/IdeaProjects/HotelApp"

cd $pd

cd HotelUsers/

echo "Building Users"

mvn clean install

docker build -t hotelapp/users .


cd $pd

cd HotelInventory/

echo "Building Inventory"

mvn clean install

docker build -t hotelapp/inventory .

cd $pd

cd HotelBilling/

echo "Building Billing"

mvn clean install

docker build -t hotelapp/billing .

cd $pd

pwd

docker-compose up