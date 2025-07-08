# billy

create customer: java -jar target/billy-1.0.0-SNAPSHOT-jar-with-dependencies.jar customer add -n "John Doe" -s "Main Street" -h "10" -p "8000" -t "Zurich" -c "CH"

create bill: java -jar target/billy-1.0.0-SNAPSHOT-jar-with-dependencies.jar bill -c fc36d159-b745-4852-ac08-f58115dd7ae0 -r 2025-07-08,100,1.5 -r 2025-07-09,50,2.0 -o my_bill.pdf
