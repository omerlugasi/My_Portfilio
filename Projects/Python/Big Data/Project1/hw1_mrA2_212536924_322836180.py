
# Extends part A1 by adding a third step:
# After filtering and counting unique air dates, it selects the best program
# based on a custom score: (number of unique air dates + number of genres).

from mrjob.job import MRJob
from mrjob.step import MRStep
from datetime import time
import re
import csv
from io import StringIO

WORD_RE = re.compile(r"[\w']+")

class MRBigBrotherPrograms(MRJob):

  # Constants:
  START_TIME = time(13, 30, 0)
  END_TIME = time(16, 29, 59)
  GOOD_GENRES = ['Reality', 'Community', 'Adventure', 'Animated']
  GOOD_LETTERS = ['p', 'w', 'm']
  BAD_LETTERS = ['a', 'b']

  SHOW_TITLE_INDEX = 0
  GENRE_INDEX = 2
  AIR_DATE_INDEX = 3
  AIR_TIME_INDEX = 4


  def steps(self):

    return [
            # Step 1: Fulfill conditions 2,3,4
            MRStep(mapper=self.mapper_conditions_234),

            # Step 2: Fulfill condition 1 and format result
            MRStep(mapper=self.mapper_filter_irrelevant_dates,
                   reducer=self.reducer_unique_dates),
            MRStep(mapper=self.mapper_title_value,
                   reducer=self.reducer_best_program)]



  def mapper_filter_irrelevant_dates(self, title, values):
    # Filter dates where the airing time is not between 13:30 and 16:29

    date = values[0]
    air_time_raw = int(values[1])
    good_genre_list = values[2]
    genre_count = values[3]

    air_time = time(air_time_raw // 10000, (air_time_raw % 10000) // 100, air_time_raw % 100)

    if self.START_TIME <= air_time <= self.END_TIME:
      # Set title as key so we can count dates after
      yield title, (date, good_genre_list, genre_count)



  def mapper_conditions_234(self, _, line):
    # Filter rows who don't satisfy conditions 2, 3 or 4

    for row in csv.reader(StringIO(line)):
      # The above line parses the CSV file into a list of rows and then iterates

      # Skip header row
      if row[self.AIR_TIME_INDEX] == "air_time":
        continue

      title = row[self.SHOW_TITLE_INDEX]
      genre_list = row[self.GENRE_INDEX]

      good_genre_list = [title]

      # Condition 2:
      condition_two = False
      for genre in self.GOOD_GENRES:
          if genre in genre_list:
            good_genre_list.append(genre)
            condition_two = True

      # Condition 3:
      counter = 0
      for letter in self.GOOD_LETTERS:
        if letter in title.lower():
          counter += 1

      condition_three = counter >= 2

      # Condition 4:
      condition_four = True
      for letter in self.BAD_LETTERS:
        if letter in title.lower():
          condition_four = False

      total_genre_count = len(genre_list.split(','))

      if condition_two and condition_three and condition_four:
        yield title, (row[self.AIR_DATE_INDEX], row[self.AIR_TIME_INDEX], good_genre_list, total_genre_count)




  def reducer_unique_dates(self, title, values):
    # Count unique dates
    dates = set()


    for air_date, good_genre_list, genre_count in values:
      dates.add(air_date)
      genre_list = good_genre_list
      num_of_genres = genre_count

    yield genre_list, (len(dates), genre_count)


  def mapper_title_value(self, key, value):
    yield None, (key[0], value[0]+value[1])


  def reducer_best_program(self, _, title_value_pairs):
    yield max(title_value_pairs, key=lambda x: x[1])


if __name__ == '__main__':
  MRBigBrotherPrograms.run()
