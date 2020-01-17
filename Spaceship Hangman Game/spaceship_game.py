import turtle
import spaceship

def main():

    # beginning game actions, selecting a random word, drawing the
    # blanks, and making a list of letters from the word
    lives = 5
    games_won = 0
    random_word = spaceship.chose_word('wordlist.txt')
    spaceship.draw_blanks(random_word)
    letter_list = list(random_word)
  
    while True:
        # prompts user to enter in letter guess and will repeatedly
        # prompt user to enter a single letter if they enter in more
        # than one
        letter_guess = input("Enter in a guess! \n")
        letter_guess = letter_guess.lower()
        while len(letter_guess) != 1:
            letter_guess = input("Enter in a single letter! \n")
        
        # determines if the guess is correct and will draw and removee
        # letter accordingly from letter list 
        if spaceship.is_guess_correct(letter_guess.lower(), random_word, lives):
            letter_list = spaceship.remove_letter(letter_guess, letter_list)
        else:
            lives -= 1

        
        # checks for wins and losses and changes games_won accordingly    
        if spaceship.check_for_win(letter_list):
            print("You win!")
            games_won += 1
            print("You have won", games_won, "games so far.")
            break
        if lives == 0:
            print ("You lose! The word was ", random_word, ".", sep="")
            print("You have won", games_won, "games so far.")
            break
    

    # prompts user to play again after every round
    while True:
        selection = input("Do you want to play spaceship again? (Y/N) \n")

        # if user wants to play again resets lives and screen and begins game
        if selection == 'Y':
            lives = 5
            turtle.clearscreen()
            random_word = spaceship.chose_word('wordlist.txt')
            spaceship.draw_blanks(random_word)
            letter_list = list(random_word)

            while True:
        
                letter_guess = input("Enter in a guess! \n")
                letter_guess = letter_guess.lower()
                while len(letter_guess) != 1:
                    letter_guess = input("Enter in a single letter! \n")
            
                if spaceship.is_guess_correct(letter_guess.lower(), random_word, lives):
                    letter_list = spaceship.remove_letter(letter_guess, letter_list)
                else:
                    lives -= 1
            
                if spaceship.check_for_win(letter_list):
                    print("You win!")
                    games_won += 1
                    print("You have won", games_won, "games so far.")
                    break
                if lives == 0:
                    print ("You lose! The word was ", random_word, ".", sep="")
                    print("You have won", games_won, "games so far.")
                    break
                
        # if user does not want to play again promps user to enter in name
        # to add to score file, then updates score file with user name and
        # amounts of games won 
        if selection == 'N':
            user_name = input("Enter your name for posterity! \n")
            leader_list = spaceship.update_leaderboard('scores.txt',user_name, games_won)
            spaceship.write_score_file('scores.txt',user_name, games_won)
            break

    

main()
