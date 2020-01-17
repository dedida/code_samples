import turtle
import random
import sys
import os

# functions that deal with gameplay begin here

def check_for_win(letter_list):
    '''
    Function is check_for_win
    parameters: letter_list (list of letters(strings) )
    returns: boolean True when letter_list is empty, False otherwise
    does: Checks if the letter_list is empty, returns True if it is
    '''
    if len(letter_list) == 0:
        return True
    else:
        return False
    
def remove_letter(letter_guess,letter_list):
    '''
    Function is remove_letter
    parameters: letter_guess (string), letter_list (list of strings)
    returns: letter_list (list)
    does: removes a letter from letter_list if it is in that list
    '''
    letter_list = [x for x in letter_list if x != letter_guess]
    return letter_list
    
def chose_word(file):
    '''
    Function is chose_word
    parameters: file (txt)
    returns: random_word (string)
    does: opens file and puts all the entries into a list, then
    uses random_choice to make a random selection, then makes the random
    word lowercase and returns it. Also checks if file exists and if file
    is empty.
    '''
    try:
        if os.stat(file).st_size == 0:
            print("File is empty")
        else:
            with open(file, "r") as infile:
                all_words = infile.read().split("\n")
                random_word = random.choice(all_words)
                random_word = random_word.lower()
            return random_word
    
    except FileNotFoundError:
        print("No file found.")
        infile = open(file, 'w')
        infile.close()

def is_guess_correct(letter_guess, random_word,lives):
    '''
    Function is is_guess_correct
    parameters: letter_guess (string), random_word (string)
    returns: boolean, True if correct, False otherwise
    does: determines if the letter/char is in the random word,
    if it is it calls draw_letter if it is not it calls
    changes_lives, draw_spaceship(), and record_bad_guess.
    '''
    if letter_guess in random_word:
        draw_letter(letter_guess,random_word)
        return True
    else:
        draw_spaceship(lives)
        record_bad_guess(letter_guess, lives)
        return False



# functions that deal with maintaining scores file begin here

def read_score_file(file):
    '''
    Function is read_score_file
    parameters: file (txt)
    returns: leader_list, a list of lists that contain 2 elements each,
    a username and score (strings)
    does: Opens up file to be read then creates and returns a list of lists
    from the lines, if the file is empty it will return an empty list, if the file
    does not exist, it will print that to the user and exit out of the program.
    '''
    
    try:
        leader_list = []
        
        if os.stat(file).st_size == 0:
            return leader_list

        # splits the separate lines in file
        with open(file, "r") as outfile:
            scoreboard = outfile.read().splitlines()

        # splits the elements in each line and appends them to the leader_list
            for i in scoreboard:
                leader = i.split()
                leader_list.append(leader)
                
        return leader_list
    
    except FileNotFoundError:
        print("No file found.")
        infile = open(file, 'w')
        infile.close()
        sys.exit()
        

def update_leaderboard(file, user_name, games_won):
    '''
    Function is update_leaderboard
    parameters: user_name (string), games_won (integer)
    returns: updated leader_list a list of lists that each contain
    a username and score (strings)
    does: makes a list with new user_name and the number of games_won
    and adds that list to the leader_list
    '''
    leader_list = read_score_file(file)
    
    if leader_list == []:
        leader_list.append(user_name +' '+ str(games_won))
    else:
        leader_list.append([user_name, str(games_won)])
        
    return leader_list


def sort(lst):
    '''
    Function is  sort
    parameters: lst (nested list), where the list inside the list contains
    only 2 elements
    returns: a sorted list
    does: sorts lists based on the second value (score) of each nested list
    in descending order
    '''
    # x represents the score which was a string, so it had to be recasted as
    # an integer in order to be used in sort for comparison

    lst.sort(key=lambda x: int(x[1]), reverse = True )

        
    return lst

def rearrange_leaderboard(file,user_name, games_won):
    '''
    Function is rearrange_leaderboard
    parameters: user_name (string), games_won (integer)
    returns: list of lists
    does: uses sort to rearrange usernames in descending order
    based on score
    '''
    leader_list = update_leaderboard(file, user_name, games_won)
    sort(leader_list)
    return leader_list
 


def write_score_file(file, user_name, games_won):
    '''
    Function is wrie_score_file
    parameters: file (txt), user_name (string), games_won (integer)
    returns: nothing
    does: rewrites file so that it contains an updated leaderboard with usernames
    listed from highest scores to lowest scores
    '''
    # calls update_leaderboard to create leader_list
    # opens file to write and if leader_list just has one nested list it writes that one line
   
    leader_list =  update_leaderboard(file, user_name, games_won)
    if len(leader_list) == 1:
        with open(file, "w") as outfile:
            outfile.write(" ".join(leader_list))
          
    # if the the leader_list has more than one entry rearrange the list and then rewrite file
    else:
        leader_list = rearrange_leaderboard(file, user_name, games_won)
        with open(file, "w") as outfile:
            if len(leader_list) == 1:
                outfile.write(" ".join(leader_list))
            else:
                for lst in leader_list:
                    outfile.write(" ".join(lst))
                    outfile.write("\n")

# functions that draw stuff begin here
    
def draw_letter(letter_guess, random_word):
    '''
    Function is draw_letter
    parameters: letter_guess (string), random_word (string)
    returns: nothing
    does: writes letter in its corresponding space
    '''

    # compares guess to all the letters in the word to find it's
    # position and writes it
    for n in range(len(random_word)):
        if letter_guess == random_word[n]:
            turtle.color('blue')
            turtle.penup()
            turtle.goto((-150 + n*33.5),200)
            turtle.pendown()
            turtle.write(letter_guess,font=("Arial", 25, "normal"))
            
def record_bad_guess(letter_guess,lives):
    '''
    Function is record_bad_guess
    parameters: letter_guess (string)
    returns: nothing
    does: writes incorrect letter on side for user to view
    '''
    turtle.color('red')
    turtle.penup()
    turtle.goto((-200 + lives*20),100)
    turtle.pendown()
    turtle.write(letter_guess,font=("Arial", 25, "normal"))

            
def draw_blanks(random_word):
    '''
    Function is draw_blanks
    parameters: random_word (string)
    returns: nothing
    does: draws blanks for each letter in a random word
    used in a round of hangman 
    '''
    
    turtle.penup()
    turtle.goto(-150,200)
    turtle.pendown()
    
    for n in range(len(random_word)):
        turtle.color('blue')
        turtle.forward(22)
        turtle.penup()
        turtle.forward(10)
        turtle.pendown()
        
def draw_spaceship(lives):
    '''
    Function is draw_spaceship
    parameters: none
    returns: nothing
    does: draws spaceship parts depending on how many lives the user
    currently has
    '''
    
    if lives == 5:
        draw_body()
    elif lives == 4:
        draw_left_rocket()
    elif lives == 3:
        draw_right_rocket()
    elif lives == 2:
        draw_left_flame()
    elif lives == 1:
        draw_right_flame()

def draw_body():
    '''
    Function is draw_body
    parameters: none
    returns: nothing
    does: draws and fills in body
    '''
    
    ship = turtle.Turtle()
    ship.begin_fill()
    ship.penup()
    ship.goto(0,175)
    ship.pendown()
    ship.goto(-50,100)
    ship.goto(-50,-100)
    ship.goto(50,-100)
    ship.goto(50,100)
    ship.goto(0,175)
    ship.end_fill()
    
    ship.color('yellow')
    ship.begin_fill()
    ship.penup()
    ship.goto(-50,100)
    ship.pendown()
    ship.goto(50,100)
    ship.goto(0,-100)
    ship.goto(-50,100)
    ship.up()
    ship.end_fill()

def draw_left_rocket():
    '''
    Function is draw_left_rocket
    parameters: none
    returns: nothing
    does: draws and fills in left rocket
    '''
    ship = turtle.Turtle()
    ship.color('grey')
    ship.begin_fill()
    ship.penup()
    ship.goto(-70,-50)
    ship.pendown()
    ship.goto(-40,-130)
    ship.goto(-100,-130)
    ship.goto(-70,-50)
    ship.end_fill()


def draw_left_flame():
    '''
    Function is draw_left_flame
    parameters: none
    returns: nothing
    does: draws and fills in left flame
    '''
    ship = turtle.Turtle()
    ship.color('red')
    ship.begin_fill()
    ship.penup()
    ship.goto(-100,-130)
    ship.pendown()
    ship.goto(-90,-150)
    ship.goto(-80,-130)
    ship.goto(-70,-160)
    ship.goto(-60,-130)
    ship.goto(-50,-150)
    ship.goto(-40,-130)
    ship.goto(-100,-130)
    ship.end_fill()
    
def draw_right_rocket():
    '''
    Function is draw_right_rocket
    parameters: none
    returns: nothing
    does: draws and fills in right rocket
    '''
    ship = turtle.Turtle()
    ship.color('grey')
    ship.begin_fill()
    ship.penup()
    ship.goto(70,-50)
    ship.pendown()
    ship.goto(40,-130)
    ship.goto(100,-130)
    ship.goto(70,-50)
    ship.end_fill()

def draw_right_flame():
    '''
    Function is draw_right_flame
    parameters: none
    returns: nothing
    does: draws and fills in right flame
    '''
    ship = turtle.Turtle()
    ship.color('red')
    ship.begin_fill()
    ship.penup()
    ship.goto(100,-130)
    ship.pendown()
    ship.goto(90,-150)
    ship.goto(80,-130)
    ship.goto(70,-160)
    ship.goto(60,-130)
    ship.goto(50,-150)
    ship.goto(40,-130)
    ship.goto(100,-130)
    ship.end_fill()

