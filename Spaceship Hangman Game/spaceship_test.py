import spaceship

# if this test suite is run more than once, make sure dummy_scores_2
# file doesn't have Scrappy in it

def test_chose_word(file, empty_file, non_existing_file):
    '''
    Function is test_chose_word
    parameters: 3 files (txt)
    returns: nothing
    does: Checks if chose_word can select a word from a file, and can
    handle an empty file, and a file that does not exist
    '''
    print("Testing chose_word")
    print("Checking file with one word...")
    if spaceship.chose_word(file) == 'word':
        print(file, "caused random selection to be made.")
    print("Checking File that is empty...")
    spaceship.chose_word(empty_file)
    print("Checking that file does not exist...")
    spaceship.chose_word(non_existing_file)
    

def test_is_guess_correct(letter_guess_list, random_word_list, expected_list,lives):
    '''
    Function is test is guess_correct
    parameters: 3 lists, letter_guess_list single character (strings),
    random_word_list words (strings), and expected_list boolean values
    returns: nothing
    does: checks if is_guess_correct outputs the expected value
    '''
    total_sucesses = 0
    total_failures = 0
                        
    for n in range(len(expected_list)):
        if spaceship.is_guess_correct(letter_guess_list[n], random_word_list[n],lives[n]) == expected_list[n]:
            print(letter_guess_list[n], "in", random_word_list[n], "is", expected_list[n] ,"...SUCCESS!")
            total_sucesses += 1
        else:
            print(letter_guess_list[n], "in", random_word_list[n], "is not",expected_list[n]  ,"...FAILURE!")
            total_failures += 1

    print("is_guess_correct Test Results")
    print("Number of tests:", len(expected_list))
    print("Number of successes:", total_sucesses)
    print("Number of failures:", total_failures)

def test_remove_letter(letter_guess,letter_list, expected_letter_list, lives):
    '''
    Function is test_remove_letter
    parameters: 3 lists, one of single letters (strings), one of nested lists with
    each letter of a word, and one nested lists with letters of a word sans
    letter_guess
    returns: nothing
    does: checks if remove_letter removes every instance of a specified letter
    in a letter_list
    '''
    total_sucesses = 0
    total_failures = 0
                        
    for n in range(len(expected_letter_list)):
        if spaceship.remove_letter(letter_guess[n],letter_list[n]) == expected_letter_list[n]:
            print(letter_guess[n],"is removed from", letter_list[n] ,"...SUCCESS!")
            total_sucesses += 1
        else:
            print(letter_guess[n],"is not removed from", letter_list[n] ,"...FAILURE!")
            total_failures += 1

    print("remove_letter Test Results")
    print("Number of tests:", len(expected_letter_list))
    print("Number of successes:", total_sucesses)
    print("Number of failures:", total_failures)

def test_check_for_win(letter_list, expected_list):
    '''
    Function is test_check_for_win
    parameters: letter_list list of single characters strings,
    expected_list list of boolean values
    returns: nothing
    does: checks if check_for_win produces the expected outcome
    '''
    letter_list = [ [], ['0'], ['e'], ["'"]]
    win_result_list = [ True, False, False, False]

    total_sucesses = 0
    total_failures = 0
                        
    for n in range(len(expected_list)):
        if spaceship.check_for_win(letter_list[n]) == expected_list[n]:
            print(letter_list[n],"= win is", expected_list[n] ,"...SUCCESS!")
            total_sucesses += 1
        else:
            print(letter_guess_list[n],"= win is not", expected_list[n] ,"...FAILURE!")
            total_failures += 1

    print("check_for_win Test Results")
    print("Number of tests:", len(expected_list))
    print("Number of successes:", total_sucesses)
    print("Number of failures:", total_failures)
    
def test_read_score_file(file, expected_list):
    '''
    Function is test_read_score_file
    parameters: file (txt), expected_list (list of what should be extracted
    from file)
    returns: nothing
    does: checks if read_score_file properly extracts data from file and puts
    it in a list correctly
    '''
   
    print("read_score_file Test Result")
    
    if spaceship.read_score_file(file) == expected_list:
        print(file, "was read correctly")
        
    else:
        print(file, "was read incorrectly")

def test_update_leaderboard(file, user_name_list, games_won_list, new_expected_list):
    '''
    Function is test_update_leaderboard
    parameters: file(txt), list of usernames (strings), list of scores (integers),
    new
    returns: nothing
    does: checks if update_leaderboard adds new username and score to leaderlist
    '''
    total_sucesses = 0
    total_failures = 0
    for n in range(len(new_expected_list)):
        if spaceship.update_leaderboard(file, user_name_list[n], games_won_list[n]) == new_expected_list[n]:
            print(user_name_list[n], "in",new_expected_list[n], "is..SUCCESS!")
            #print(spaceship.update_leaderboard(file, user_name_list[n], games_won_list[n])
            total_sucesses += 1
        else:
           print(user_name_list[n], "in",new_expected_list[n], "is...FAILURE")
           print(spaceship.update_leaderboard(file, user_name_list[n], games_won_list[n]))
           print(new_expected_list[n])
           total_failures += 1

    print("update_leaderboard Test Results")
    print("Number of tests:", len(new_expected_list))
    print("Number of successes:", total_sucesses)
    print("Number of failures:", total_failures)

def test_sort(lst, sort_expected_list):
    '''
    Function is test_sort
    parameters: lst is list of 2 element nested lists, where the second element
    is an integer that is a string, and the sort_expected_list is also a list
    of 2 element nested list,where the second element is an integer that is a string
    returns: nothing
    does: checks if the sort function sorts the lists in descending order based
    on the second element
    side note: this also acts as a test for reaarange_leaderboard because that
    function just calls sort to give a sorted list
    '''
    total_sucesses = 0
    total_failures = 0
    
    for n in range(len(sort_expected_list)):
        if spaceship.sort(lst[n]) == sort_expected_list[n]:
            print(lst[n], "when sorted equals", sort_expected_list[n],"...SUCCESS!")
            total_sucesses += 1
        else:
            print(lst[n], "when sorted does not equal", sort_expected_list[n],"...FAILURE!")
            total_failures += 1
    
    print("sort Test Results")
    print("Number of tests:", len(sort_expected_list))
    print("Number of successes:", total_sucesses)
    print("Number of failures:", total_failures)
    
    
def test_write_score_file(file, user_name, games_won,expected_updated_list):
    '''
    Function is test_write_score_file
    parameters: file(txt), user_name (string), games_won(int), expected_updated_list
    returns: nothing
    does: checks if write_score_file updates the file properly
    '''
    spaceship.write_score_file(file, user_name, games_won)

    print("Checking if file updated properly using write_score_file")
    if spaceship.read_score_file(file) == expected_updated_list:
        print("File updated properly!")
    else:
        print("File not updated properly!")
              
def main():
    test_chose_word('dummy_wordlist.txt','dummy_empty_wordlist.txt', 'non_existing_file.txt')
    print("")
    
    lives = [5,5,5,5,5,5,5]
    letter_guess_list = ['8', "'", 'H', 'y', 'a', '*', '1']
    random_word_list = ['128', "can't", 'Hello', 'yEET', 'spin', '@$wa', 'yikes33']
    expected_list = [True, True, True, True, False, False, False]

    test_is_guess_correct(letter_guess_list, random_word_list, expected_list,lives)
    print("")

    lives = [5,5,5,5,5]
    letter_guess = ['a', 'B',"'", '4', '!']
    letter_list = [['a','b','b','a'],['B','B','c'],['h','e',"'",'l','l'],
                   ['1','2','4'],['.','_','.']]
    expected_letter_list = [['b','b'],['c'],['h','e','l','l'],['1','2'],['.','_','.']]
    
    test_remove_letter(letter_guess, letter_list, expected_letter_list,lives)
    print("")
    
    letter_list = [ [], ['0'], ['e'], ["'"]]
    win_result_list = [ True, False, False, False]

    test_check_for_win(letter_list, win_result_list)
    print("")
    
    expected_list = [['Scooby', '9'],['Shaggy', '7'],['Velma', '6']]
    test_read_score_file('dummy_scores.txt', expected_list)
    print("")
    
    user_name_list = ['Fred', 'Daphne', 'Scooby']
    games_won_list = [ 5, 5, 3]
    new_expected_list = [ [['Scooby', '9'],['Shaggy', '7'],['Velma', '6'],['Fred', '5']],
                          [['Scooby', '9'],['Shaggy', '7'],['Velma', '6'], ['Daphne', '5']],
                          [['Scooby', '9'],['Shaggy', '7'],['Velma', '6'], ['Scooby', '3']]]


    test_update_leaderboard('dummy_scores.txt', user_name_list, games_won_list, new_expected_list)
    print("")

    lst =  [ [['A','8'],['B','3']], [['B', '0'],['A','2'],['B','0']],
             [['C','4'],['D','-5'],['B','6'],['A','10']] ]
    sort_expected_list = [ [['A','8'],['B','3']], [['A','2'],['B','0'],['B','0']],
                           [['A','10'],['B','6'],['C','4'],['D','-5'],] ]
    
    test_sort(lst, sort_expected_list)
    print("")

    user_name = 'Scrappy'
    games_won = 8
    expected_updated_list =[['Scooby','9'],['Scrappy','8'],['Shaggy','7'],
                             ['Daphne','7'],['Velma','6'],['Freddy','5'] ]

    test_write_score_file('dummy_scores_2.txt', user_name, games_won, expected_updated_list)


main()
