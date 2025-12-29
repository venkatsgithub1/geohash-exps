import os
import csv
import json

os.chdir('.')
print(os.getcwd())
with open('./src/main/resources/output/place_data.csv', 'w') as opfile1, open('./src/main/resources/input/zomato_locations.csv', 'r') as file1:
    file1Reader = csv.reader(file1)
    file1Writer = csv.writer(opfile1)
    header_row = next(file1Reader)
    for row in file1Reader:
        name_of_place = row[0]
        latitude = row[2]
        longitude = row[3]
        file1Writer.writerow((name_of_place, latitude, longitude))

with open('./src/main/resources/output/venue_data.json', 'w') as opfile2, open('./src/main/resources/input/Bangalore_venues.csv', 'r') as file2, open('./src/main/resources/output/venue_categories.csv','w') as opfile21:
    file2Reader = csv.reader(file2)
    header_row = next(file2Reader)
    venue_data = {}
    for row in file2Reader:
        category = row[2]
        if category not in venue_data:
            venue_data[category] = []
        venue_data[category].append({"name": row[1], "latitude": row[3], "longitude": row[4], "rating": row[8]})
    json.dump(venue_data, opfile2, indent=4)
    csvwriter2 = csv.writer(opfile21)
    for key in venue_data.keys():
        csvwriter2.writerow((key,))
