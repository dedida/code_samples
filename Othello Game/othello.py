'''
Deanna Dang
CS 5001
HW#7 Full Othello Game
December 7, 2018
'''
import turtle
import os
import sys


# code to draw board is the one provided, no changes were madew to it
SQUARE = 50
def draw_board(n):
    ''' Function: draw_board
        Parameters: n, an int for # of squares
        Returns: nothing
        Does: Draws an nxn board with a green background and initial starting
        tiles
    '''

    turtle.setup(n * SQUARE + SQUARE, n * SQUARE + SQUARE)
    turtle.screensize(n * SQUARE, n * SQUARE)
    turtle.bgcolor('white')

    # Create the turtle to draw the board
    othello = turtle.Turtle()
    othello.penup()
    othello.speed(0)
    othello.hideturtle()
    # Line color is black, fill color is green
    othello.color("black", "forest green")
    
    # Move the turtle to the upper left corner
    corner = -n * SQUARE / 2
    othello.setposition(corner, corner)
  
    # Draw the green background
    othello.begin_fill()
    for i in range(4):
        othello.pendown()
        othello.forward(SQUARE * n)
        othello.left(90)
    othello.end_fill()

    # Draw the horizontal lines
    for i in range(n + 1):
        othello.setposition(corner, SQUARE * i + corner)
        draw_lines(othello, n)

    # Draw the vertical lines
    othello.left(90)
    for i in range(n + 1):
        othello.setposition(SQUARE * i + corner, corner)
        draw_lines(othello, n)
    turtle.hideturtle()
    turtle.speed(0)
    turtle.penup()

def draw_lines(turt, n):
    turt.pendown()
    turt.forward(SQUARE * n)
    turt.penup()

def draw_starting_tiles(board_size):
    '''
    Function: draw_starting_tiles
    Parameters: board_size, int
    Returns: nothing
    Does: Draws 4 initial starting tiles in the middle of board
    '''
    for i in range((board_size//2) - 1, (board_size//2) + 2):
        for j in range((board_size//2) - 1, (board_size//2) + 2):
            if i == ((board_size//2) - 1) or  i == (board_size//2):
                if j == i or j == (i + 1) or j == (i - 1) :
                    y = 155 - (i * 50)
                    x = -175 + (j * 50)
                    turtle.penup()
                    turtle.speed(0)
                    turtle.goto(x,y)
                    turtle.pendown()
                    turtle.color(Board.complete_board[i][j].color)
                    turtle.begin_fill()
                    turtle.circle(20)
                    turtle.end_fill()
                      
class Tile:


    def __init__(self, board_size = 8):
        '''
        Method __init__
        parameters: optional board_size
        returns: nothing
        does: creates instance of object
        '''
        self.turns = 0
        self.color = 'forest green'
        self.board_size = 8
        self.filename = 'scores.txt'
        
    def __eq__(self, other):
        '''
        Method __eq__
        parameters: another object
        returns: boolean True
        does: 
        '''
        return self.turns == other.turns and self.color == other.color
            
    def __str__(self):
        '''
        Method __str__
        parameters: none 
        returns: string detailing state of attributes
        does: creates and returns string of current attributes
        '''
        string = 'This board is of size' + self.board_size + '\n' \
                'There has been ' + self.turns + '.' +  '\n' \
                 + ' This tile is' + self.color + '.'
        
        return string
        
    def get_coordinates(self, x, y):
        '''
        Method get_coordinate
        parameters: x and y floats 
        returns: none
        does: After user clicks somewhere determines whether or not that click was
        on the board, and if it was on the board determines if that square is
        available, then determines if that move can flip any tiles. Then tells
        player if move is legal, if it is increases amount of turns and calls
        computer to make move. Also checks if legal moves are available for next player,
        if no moves are legal, skips player's turn.
        '''
 
        # determines if user clicks somewhere on gameboard 
        if x >= 8 * 25 or x <= -8 * 25 or y >= 8 * 25 or y <= -8 * 25:
            print('Out of bounds! Please click somewhere on the board.')

         # changes coordinates to general row and column numbers
        else:
            x_1 = x + (50 * (8 - 1))
            y_1 = y - (50 * (8 - 1))
            column = round((abs(x_1)//50) - 3)
            row = round((abs(y_1)//50) - 3)
     
            # tells user whether or not there is a tile already on that square
            if Board.complete_board[row][column].color != 'forest green':
                print('That square already has a tile!')
            else:
                # checks if the move is legal
                # also draws/flips tiles and increases number of turns
                a = self.check_horizontal(row, column) 
                b = self.check_vertical(row, column) 
                c = self.check_down_diagonal(row, column) 
                d = self.check_up_diagonal(row, column)

                if a or b or c or d:
                    
                    Board.complete_board[row][column].color = self.get_your_color()
                    self.draw_new_tile(row, column)
                    self.turns += 1
                else:
                    print('That is not a legal move!')
                # checks if game is over and if legal move exists for next player
                if self.check_for_win():
                    self.check_moves_left()

                # controls when the computer makes a move and changes number of turns 
                while self.turns % 2 != 0:
                    if self.turns <= 30:
                        move_pos = self.make_ai_go_small_move()
                        row = move_pos[0]
                        column = move_pos[1]
                        self.draw_new_tile(row, column)
                    
                        a = self.check_horizontal(row,column)
                        b = self.check_vertical(row, column) 
                        c = self.check_down_diagonal(row, column) 
                        d = self.check_up_diagonal(row, column)
                    
                        Board.complete_board[row][column].color = self.get_your_color()
                        self.draw_new_tile(row, column)
                        self.turns += 1
                    else:
                        move_pos = self.make_ai_go_big_move()
                        row = move_pos[0]
                        column = move_pos[1]

                        self.draw_new_tile(row, column)
                    
                        a = self.check_horizontal(row,column)
                        b = self.check_vertical(row, column) 
                        c = self.check_down_diagonal(row, column) 
                        d = self.check_up_diagonal(row, column)
                    
                        Board.complete_board[row][column].color = self.get_your_color()
                        self.draw_new_tile(row, column)
                        self.turns += 1

                    if self.check_for_win():
                        self.check_moves_left()
                
                
    def make_ai_go_big_move(self):
        '''
        Method make_ai_go_big_move
        parameters: none 
        returns: none
        does: looks at all the possible moves on the board, and determines
        the move that will flip the most tiles
        '''
        horiz_tiles = 0
        vert_tiles = 0
        down_diag_tiles = 0
        up_diag_tiles = 0
        h_i = 0
        h_j = 0
        v_i = 0
        v_j = 0
        d_i = 0
        d_j = 0
        u_i = 0
        u_j = 0
        # loops through all spaces on board and looks for an available move
        # that produces the flip with the highest amount of tiles
        for i in range(0, self.board_size):
            for j in range(0, self.board_size):
                if Board.complete_board[i][j].color == 'forest green':
                    if self.check_viable_horizontal(i, j):
                        if self.check_ai_horizontal(i, j) > horiz_tiles:
                            horiz_tiles = self.check_ai_horizontal(i, j)
                            h_i = i
                            h_j = j
                    if self.check_viable_vertical(i, j):
                        if self.check_ai_vertical(i, j) > vert_tiles:
                            vert_tiles = self.check_ai_vertical(i, j)
                            v_i = i
                            v_j = j
                    if self.check_viable_down_diagonal(i, j):
                        if self.check_ai_down_diagonal(i, j) > down_diag_tiles:
                            down_diag_tiles = self.check_ai_down_diagonal(i, j)
                            d_i = i
                            d_j = j
                    if self.check_viable_up_diagonal(i, j):
                        if self.check_ai_up_diagonal(i, j) > up_diag_tiles:
                            up_diag_tiles = self.check_ai_up_diagonal(i, j)
                            u_i = i
                            u_j = j
               
        num_tile_flipped = [horiz_tiles, vert_tiles, down_diag_tiles, up_diag_tiles]
        max_tiles = max(num_tile_flipped)
        position = num_tile_flipped.index(max_tiles)
        
        tile_location = [[h_i, h_j], [v_i, v_j], [d_i, d_j], [u_i, u_j]]
        return tile_location[position]
        
    def make_ai_go_small_move(self):
        '''
        Method make_ai_go_small_move
        parameters: none 
        returns: none
        does: looks at all the possible moves on the board, and determines
        the move that will flip the least amount of tiles
        '''
        horiz_tiles = 8
        vert_tiles = 8
        down_diag_tiles = 8
        up_diag_tiles = 8
        h_i = 0
        h_j = 0
        v_i = 0
        v_j = 0
        d_i = 0
        d_j = 0
        u_i = 0
        u_j = 0
        
        # loops through all spaces on board and looks for an available move
        # that produces the flip with the least amount of tiles
        for i in range(0, self.board_size):
            for j in range(0, self.board_size):
                if Board.complete_board[i][j].color == 'forest green':
                    if self.check_viable_horizontal(i, j):
                        if self.check_ai_horizontal(i, j) < horiz_tiles:
                            horiz_tiles = self.check_ai_horizontal(i, j)
                            h_i = i
                            h_j = j
                    if self.check_viable_vertical(i, j):
                        if self.check_ai_vertical(i, j) < vert_tiles:
                            vert_tiles = self.check_ai_vertical(i, j)
                            v_i = i
                            v_j = j
                    if self.check_viable_down_diagonal(i, j):
                        if self.check_ai_down_diagonal(i, j) < down_diag_tiles:
                            down_diag_tiles = self.check_ai_down_diagonal(i, j)
                            d_i = i
                            d_j = j
                    if self.check_viable_up_diagonal(i, j):
                        if self.check_ai_up_diagonal(i, j) < up_diag_tiles:
                            up_diag_tiles = self.check_ai_up_diagonal(i, j)
                            u_i = i
                            u_j = j
               
        num_tile_flipped = [horiz_tiles, vert_tiles, down_diag_tiles, up_diag_tiles]
        max_tiles = min(num_tile_flipped)
        position = num_tile_flipped.index(max_tiles)
        
        tile_location = [[h_i, h_j], [v_i, v_j], [d_i, d_j], [u_i, u_j]]
        return tile_location[position]

    def check_ai_horizontal(self,row,column):
        '''
        Method check_ai_horizontal
        parameters: row (int), column (int)  
        returns: number of tiles flipped, int
        does: determines if the down horizontal is a viable flip
        and how many tiles will be flipped
        '''
        right = 'right'
        right_tiles = False
        right_end = False
        end_right = 0
        tile_count_right = 0
        for n in range(column + 1, self.board_size):
            if Board.complete_board[row][n].color == 'forest green':
                break
            elif Board.complete_board[row][n].color == self.get_your_color():
                right_end = True
                break
            elif Board.complete_board[row][n].color == self.get_opponent_color():
                right_tiles = True
                end_right = n
                tile_count_right += 1
            
        left = 'left'
        left_tiles = False
        left_end = False
        end_left = 0
        tile_count_left = 0
        
        for n in range(column - 1, -1, -1):
            if Board.complete_board[row][n].color == 'forest green':
                break
            if Board.complete_board[row][n].color == self.get_your_color():
                left_end = True
                break
            elif Board.complete_board[row][n].color == self.get_opponent_color():
                left_tiles = True
                end_left = n
                tile_count_left += 1
        total_tiles = 0
        if (right_tiles and right_end):
            total_tiles += tile_count_right
        if (left_tiles and left_end):
            total_tiles += tile_count_left

        return total_tiles
        
    def check_ai_vertical(self,row,column):
        '''
        Method check_ai_vertical
        parameters: row (int), column (int) 
        returns: number of tiles flipped, int
        does: determines if the vertical is a viable flip
        and how many tiles will be flipped
        '''
        down = 'down'
        down_tiles = False
        down_end = False
        end_down = 0
        down_tiles_count = 0
        for n in range(row + 1, self.board_size):
            if Board.complete_board[n][column].color == 'forest green':
                break
            if Board.complete_board[n][column].color == self.get_opponent_color():
                down_tiles = True
                end_down = n
                down_tiles_count += 1
            if Board.complete_board[n][column].color == self.get_your_color():
                down_end = True
                break
            
        up = 'up'
        up_tiles = False
        up_end = False
        end_up = 0
        up_tiles_count = 0
        for n in range(row - 1, -1, -1):
            if Board.complete_board[n][column].color == 'forest green':
                break
            elif Board.complete_board[n][column].color == self.get_your_color():
                up_end = True
                break
            elif Board.complete_board[n][column].color == self.get_opponent_color():
                up_tiles = True
                end_up = n
                up_tiles_count += 1
                
        total_tiles = 0
        if (down_tiles and down_end):
            total_tiles += down_tiles_count
        if (up_tiles and up_end):
            total_tiles += up_tiles_count

        return total_tiles
      

    def check_ai_down_diagonal(self,row,column):
        '''
        Method check_ai_down_diagonal
        parameters: row (int), column (int)  
        returns: number of tiles flipped, int
        does: determines if the down diagonal is a viable flip
        and how many tiles will be flipped
        '''
        down = 'down'
        down_tiles = False
        down_end = False
        end_i = 0
        end_j = 0
        i = row + 1
        j = column + 1
        down_tiles_count = 0
        while i <= 7 and j <= 7:
            if Board.complete_board[i][j].color == 'forest green':
                break
            elif Board.complete_board[i][j].color == self.get_your_color():
                down_end = True
                break
            elif Board.complete_board[i][j].color == self.get_opponent_color():
                down_tiles = True
                end_i = i
                end_j = j
                down_tiles_count += 1
            i += 1
            j += 1
            
        up = 'up'
        up_tiles = False
        up_end = False
        end_i = 0
        end_j = 0
        i = row - 1
        j = column - 1
        up_tiles_count = 0
        while i >= 0 and j >= 0:
            if Board.complete_board[i][j].color == 'forest green':
                break
            elif Board.complete_board[i][j].color == self.get_your_color():
                up_end = True
                break
            elif Board.complete_board[i][j].color == self.get_opponent_color():
                up_tiles = True
                end_i = i
                end_j = j
                up_tiles_count += 1
            i -= 1
            j -= 1
            
        total_tiles = 0
        if (down_tiles and down_end):
            total_tiles += down_tiles_count
        if (up_tiles and up_end):
            total_tiles += up_tiles_count

        return total_tiles 
    
    def check_ai_up_diagonal(self,row,column):
        '''
        Method check_ai_up_diagonal
        parameters: row (int), column (int)  
        returns: number of tiles flipped, int
        does: determines if the up diagonal is a viable flip
        and how many tiles will be flipped
        '''
        down = 'down'
        down_tiles = False
        down_end = False
        end_i = 0
        end_j = 0
        i = row + 1
        j = column - 1
        down_tiles_count = 0
        while i <= 7 and j >= 0:
            if Board.complete_board[i][j].color == 'forest green':
                break
            elif Board.complete_board[i][j].color == self.get_your_color():
                down_end = True
                break
            elif Board.complete_board[i][j].color == self.get_opponent_color():
                down_tiles = True
                end_i = i
                end_j = j
                down_tiles_count += 1
            i += 1
            j -= 1
            
        up = 'up'
        up_tiles = False
        up_end = False
        end_i = 0
        end_j = 0
        i = row - 1
        j = column + 1
        up_tiles_count = 0
        while i >= 0 and j <= 7:
            if Board.complete_board[i][j].color == 'forest green':
                break
            elif Board.complete_board[i][j].color == self.get_your_color():
                up_end = True
                break
            elif Board.complete_board[i][j].color == self.get_opponent_color():
                up_tiles = True
                end_i = i
                end_j = j
                up_tiles_count += 1
            i -= 1
            j += 1
        
            
        total_tiles = 0
        if (down_tiles and down_end):
            total_tiles += down_tiles_count
        if (up_tiles and up_end):
            total_tiles += up_tiles_count

        return total_tiles 
            
    def check_moves_left(self):
        '''
        Method check_moves_left
        parameters: none
        returns: none
        does: determines if move exists for current player, if not increases
        turn by one and checks if move exists for next player
        '''
        if self.check_if_legal_move_exists():
            self.turns += 1
            if self.check_if_legal_move_exists(): 
                print("No more moves for either player!")
                self.get_points()
                    
    def check_if_legal_move_exists(self):
        '''
        Method check_if_legal_move_exists
        parameters: Boolean, True if no legal move exists
        returns: none
        does: looks at all the spaces on the board that have a
        green tile and determines if any legal move can be made
        from that location
        '''
        for i in range(0, self.board_size):
            for j in range(0, self.board_size):
                if Board.complete_board[i][j].color == 'forest green':
                    if (self.check_viable_horizontal(i, j) or
                        self.check_viable_vertical(i, j) or
                        self.check_viable_down_diagonal(i, j) or
                        self.check_viable_up_diagonal(i, j)):

                        return False
        
        print('No legal moves, next player go!')
        return True

    
    def check_viable_horizontal(self, row, column):
        '''
        Method check_viable_horizontal
        parameters: row (int), column (int)
        returns: Boolean, True if there are tiles that can be flipped
        on the horizontal
        does: determines if the new move is able to flip tiles on the horizontal
        by looking at all the tiles above and below the new tile on the horizontal
        '''
        right = 'right'
        right_tiles = False
        right_end = False
        end_right = 0
        for n in range(column + 1, self.board_size):
            if Board.complete_board[row][n].color == 'forest green':
                break
            elif Board.complete_board[row][n].color == self.get_your_color():
                right_end = True
                break
            elif Board.complete_board[row][n].color == self.get_opponent_color():
                right_tiles = True
                end_right = n
            
        left = 'left'
        left_tiles = False
        left_end = False
        end_left = 0
        for n in range(column - 1, -1, -1):
            if Board.complete_board[row][n].color == 'forest green':
                break
            if Board.complete_board[row][n].color == self.get_your_color():
                left_end = True
                break
            elif Board.complete_board[row][n].color == self.get_opponent_color():
                left_tiles = True
                end_left = n
        
        if (right_tiles and right_end) or (left_tiles and left_end):
            return True
        else:
            return False
        
    def check_viable_vertical(self, row, column):
        '''
        Method check_viable_vertical
        parameters: row (int), column (int)
        returns: Boolean, True if there are tiles that can be flipped
        on the vertical
        does: determines if the new move is able to flip tiles on the vertical
        by looking at all the tiles above and below the new tile on the vertical
        '''
        down = 'down'
        down_tiles = False
        down_end = False
        end_down = 0
        for n in range(row + 1, self.board_size):
            if Board.complete_board[n][column].color == 'forest green':
                break
            if Board.complete_board[n][column].color == self.get_opponent_color():
                down_tiles = True
                end_down = n
            if Board.complete_board[n][column].color == self.get_your_color():
                down_end = True
                break
            
        up = 'up'
        up_tiles = False
        up_end = False
        end_up = 0
        for n in range(row - 1, -1, -1):
            if Board.complete_board[n][column].color == 'forest green':
                break
            elif Board.complete_board[n][column].color == self.get_your_color():
                up_end = True
                break
            elif Board.complete_board[n][column].color == self.get_opponent_color():
                up_tiles = True
                end_up = n
        
        if (down_tiles and down_end) or (up_tiles and up_end):
            return True
        else:
            return False

    def check_viable_down_diagonal(self, row, column):
        '''
        Method check_viable_down_diagonal
        parameters: row (int), column (int)
        returns: Boolean, True if there are tiles that can be flipped
        in the diagonal
        does: determines if the new move is able to flip tiles on the downward
        diagonal by looking at all the tiles above and below the new tile
        on the downward diagonal
        '''
        down = 'down'
        down_tiles = False
        down_end = False
        end_i = 0
        end_j = 0
        i = row + 1
        j = column + 1
        while i <= 7 and j <= 7:
            if Board.complete_board[i][j].color == 'forest green':
                break
            elif Board.complete_board[i][j].color == self.get_your_color():
                down_end = True
                break
            elif Board.complete_board[i][j].color == self.get_opponent_color():
                down_tiles = True
                end_i = i
                end_j = j
            i += 1
            j += 1
            
        up = 'up'
        up_tiles = False
        up_end = False
        end_i = 0
        end_j = 0
        i = row - 1
        j = column - 1
        while i >= 0 and j >= 0:
            if Board.complete_board[i][j].color == 'forest green':
                break
            elif Board.complete_board[i][j].color == self.get_your_color():
                up_end = True
                break
            elif Board.complete_board[i][j].color == self.get_opponent_color():
                up_tiles = True
                end_i = i
                end_j = j
            i -= 1
            j -= 1
        
        if (down_tiles and down_end) or (up_tiles and up_end):
            return True
        else:
            return False
        
    def check_viable_up_diagonal(self, row, column):
        '''
        Method check_viable_up_diagonal
        parameters: row (int), column (int)
        returns: Boolean, True if there are tiles that can be flipped
        in the diagonal
        does: determines if the new move is able to flip tiles on the upward
        diagonal by looking at all the tiles above and below the new tile
        on the upward diagonal
        '''
        down = 'down'
        down_tiles = False
        down_end = False
        end_i = 0
        end_j = 0
        i = row + 1
        j = column - 1
        while i <= 7 and j >= 0:
            if Board.complete_board[i][j].color == 'forest green':
                break
            elif Board.complete_board[i][j].color == self.get_your_color():
                down_end = True
                break
            elif Board.complete_board[i][j].color == self.get_opponent_color():
                down_tiles = True
                end_i = i
                end_j = j
            i += 1
            j -= 1
            
        up = 'up'
        up_tiles = False
        up_end = False
        end_i = 0
        end_j = 0
        i = row - 1
        j = column + 1
        while i >= 0 and j <= 7:
            if Board.complete_board[i][j].color == 'forest green':
                break
            elif Board.complete_board[i][j].color == self.get_your_color():
                up_end = True
                break
            elif Board.complete_board[i][j].color == self.get_opponent_color():
                up_tiles = True
                end_i = i
                end_j = j
            i -= 1
            j += 1
        
        if (down_tiles and down_end) or (up_tiles and up_end):
            return True
        else:
            return False
            
                    
    def draw_new_tile(self, row, column):
        '''
        Method draw_new_tile
        parameters: row (int), column (int)
        returns: none
        does: draws the tile on the square the user selected
        '''
        y = 155 - (row * 50)
        x = -175 + (column * 50)
        turtle.penup()
        turtle.speed(0)
        turtle.goto(x,y)
        turtle.pendown()
        turtle.color(Board.complete_board[row][column].color)
        turtle.begin_fill()
        turtle.circle(20)
        turtle.end_fill()
        
                    
    def get_opponent_color(self):
        '''
        Method get_opponent_color
        parameters: none
        returns: none
        does: determines the opponent's tile color based on the number
        of turns that have happened
        '''
        if self.turns % 2 == 0:
            color = 'white'
        else:
            color = 'black'
        return color
             
    def get_your_color(self):
        '''
        Method get_your_color
        parameters: none
        returns: none
        does: determines the user's tile color based on the number
        of turns that have happened
        '''
        if self.turns % 2 == 0:
            color = 'black'
        else:
            color = 'white'
        return color
    
    def check_horizontal(self, row, column):
        '''
        Method check_horizontal
        parameters: row (int), column (int)
        returns: none
        does: determines if any tiles can be flipped
        horizontally from a move and calls method that flips
        horizontally placed tiles
        '''
        right = 'right'
        right_tiles = False
        right_end = False
        end_right = 0
        for n in range(column + 1, self.board_size):
            if Board.complete_board[row][n].color == 'forest green':
                break
            elif Board.complete_board[row][n].color == self.get_your_color():
                right_end = True
                break
            elif Board.complete_board[row][n].color == self.get_opponent_color():
                right_tiles = True
                end_right = n
            
        left = 'left'
        left_tiles = False
        left_end = False
        end_left = 0
        for n in range(column - 1, -1, -1):
            if Board.complete_board[row][n].color == 'forest green':
                break
            if Board.complete_board[row][n].color == self.get_your_color():
                left_end = True
                break
            elif Board.complete_board[row][n].color == self.get_opponent_color():
                left_tiles = True
                end_left = n
           
        if right_tiles and right_end:
            self.flip_horizontal(row, column, end_right, right)
        
        if left_tiles and left_end:
            self.flip_horizontal(row, column, end_left, left)
        
        if (right_tiles and right_end) or (left_tiles and left_end):
            return True
        else:
            return False

    def flip_horizontal(self, row, column, end_pos, direction):
        '''
        Method flip_horizontal
        parameters: row (int), column(int), end_row (int), end_pos (int), and
        direction (string)
        returns: none
        does: flips the color all the horizontally placed tiles in between the new
        tile and a tile of that same color
        '''
        if direction == 'right':
            for n in range(column + 1, end_pos + 1):
                Board.complete_board[row][n].color = self.get_your_color()
                y = 155 - (row * 50)
                x = -175 + (n * 50)
                turtle.penup()
                turtle.speed(0)
                turtle.goto(x,y)
                turtle.pendown()
                turtle.color(Board.complete_board[row][n].color)
                turtle.begin_fill()
                turtle.circle(20)
                turtle.end_fill()
        if direction == 'left':
            for n in range(column - 1, end_pos - 1, -1):
                Board.complete_board[row][n].color = self.get_your_color()
                y = 155 - (row * 50)
                x = -175 + (n * 50)
                turtle.penup()
                turtle.speed(0)
                turtle.goto(x,y)
                turtle.pendown()
                turtle.color(Board.complete_board[row][n].color)
                turtle.begin_fill()
                turtle.circle(20)
                turtle.end_fill()
    
    def check_vertical(self, row, column):
        '''
        Method check_vertical
        parameters: row (int), column (int)
        returns: none
        does: determines if any tiles can be flipped
        vertically from a move and calls method that flips
        vertically placed tiles
        '''
        down = 'down'
        down_tiles = False
        down_end = False
        end_down = 0
        for n in range(row + 1, self.board_size):
            if Board.complete_board[n][column].color == 'forest green':
                break
            if Board.complete_board[n][column].color == self.get_opponent_color():
                down_tiles = True
                end_down = n
            if Board.complete_board[n][column].color == self.get_your_color():
                down_end = True
                break
            
        up = 'up'
        up_tiles = False
        up_end = False
        end_up = 0
        for n in range(row - 1, -1, -1):
            if Board.complete_board[n][column].color == 'forest green':
                break
            elif Board.complete_board[n][column].color == self.get_your_color():
                up_end = True
                break
            elif Board.complete_board[n][column].color == self.get_opponent_color():
                up_tiles = True
                end_up = n
          

        if down_tiles and down_end:
            self.flip_vertical(row, column, end_down, down)
        
        if up_tiles and up_end:
            self.flip_vertical(row, column, end_up, up)
        
        if (down_tiles and down_end) or (up_tiles and up_end):
            return True
        else:
            return False

    def flip_vertical(self, row, column, end_pos, direction):
        '''
        Method flip_vertical
        parameters: row (int), column(int), end_row (int), end_pos (int), and
        direction (string)
        returns: none
        does: flips the color all the vertically placed tiles in between the new
        tile and a tile of that same color
        '''
        if direction == 'down':
            for n in range(row + 1, end_pos + 1):
                Board.complete_board[n][column].color = self.get_your_color()
                y = 155 - (n * 50)
                x = -175 + (column * 50)
                turtle.penup()
                turtle.speed(0)
                turtle.goto(x,y)
                turtle.pendown()
                turtle.color(Board.complete_board[n][column].color)
                turtle.begin_fill()
                turtle.circle(20)
                turtle.end_fill()
        if direction == 'up':
            for n in range(row - 1, end_pos - 1, -1):
                Board.complete_board[n][column].color = self.get_your_color()
                y = 155 - (n * 50)
                x = -175 + (column * 50)
                turtle.penup()
                turtle.speed(0)
                turtle.goto(x,y)
                turtle.pendown()
                turtle.color(Board.complete_board[n][column].color)
                turtle.begin_fill()
                turtle.circle(20)
                turtle.end_fill()
    
    def check_down_diagonal(self, row, column):
        '''
        Method check_down_diagonal
        parameters: row (int), column (int)
        returns: none
        does: determines if any tiles can be flipped
        diagonally from a move and calls method that flips
        down diagonal tiles
        '''
        down = 'down'
        down_tiles = False
        down_end = False
        end_i = 0
        end_j = 0
        i = row + 1
        j = column + 1
        while i <= 7 and j <= 7:
            if Board.complete_board[i][j].color == 'forest green':
                break
            elif Board.complete_board[i][j].color == self.get_your_color():
                down_end = True
                break
            elif Board.complete_board[i][j].color == self.get_opponent_color():
                down_tiles = True
                end_i = i
                end_j = j
            i += 1
            j += 1
            
        if down_tiles and down_end:
            self.flip_down_diagonal(row, column, end_i, end_j, down)
            
        up = 'up'
        up_tiles = False
        up_end = False
        end_i = 0
        end_j = 0
        i = row - 1
        j = column - 1
        while i >= 0 and j >= 0:
            if Board.complete_board[i][j].color == 'forest green':
                break
            elif Board.complete_board[i][j].color == self.get_your_color():
                up_end = True
                break
            elif Board.complete_board[i][j].color == self.get_opponent_color():
                up_tiles = True
                end_i = i
                end_j = j
            i -= 1
            j -= 1
        
        if up_tiles and up_end:
            self.flip_down_diagonal(row, column, end_i, end_j, up)
        
        if (down_tiles and down_end) or (up_tiles and up_end):
            return True
        else:
            return False

    def flip_down_diagonal(self, row, column, end_row, end_column, direction):
        '''
        Method flip_down_diagonal 
        parameters: row (int), column(int), end_row (int), end_column (int), and
        direction (string)
        returns: none
        does: flips the color all the diagonally placed tiles in between the new
        tile and a tile of that same color
        '''
        i = row + 1
        j = column + 1
        if direction == 'down':
            while i <= end_row and j <= end_column: 
                Board.complete_board[i][j].color = self.get_your_color()
                y = 155 - (i * 50)
                x = -175 + (j * 50)
                turtle.penup()
                turtle.speed(0)
                turtle.goto(x,y)
                turtle.pendown()
                turtle.color(Board.complete_board[i][j].color)
                turtle.begin_fill()
                turtle.circle(20)
                turtle.end_fill()
                i += 1
                j += 1
                
        i = row - 1
        j = column - 1
        if direction == 'up':
            while i >= end_row and j >= end_column:
                Board.complete_board[i][j].color = self.get_your_color()
                y = 155 - (i * 50)
                x = -175 + (j * 50)
                turtle.penup()
                turtle.speed(0)
                turtle.goto(x,y)
                turtle.pendown()
                turtle.color(Board.complete_board[i][j].color)
                turtle.begin_fill()
                turtle.circle(20)
                turtle.end_fill()
                i -= 1
                j -= 1
    
    def check_up_diagonal(self, row, column):
        '''
        Method check_up_diagonal
        parameters: row (int), column (int)
        returns: none
        does: determines if any tiles can be flipped
        diagonally from a move and calls method
        that flips up diagonal tiles
        '''
        down = 'down'
        down_tiles = False
        down_end = False
        end_i = 0
        end_j = 0
        i = row + 1
        j = column - 1
        while i <= 7 and j >= 0:
            if Board.complete_board[i][j].color == 'forest green':
                break
            elif Board.complete_board[i][j].color == self.get_your_color():
                down_end = True
                break
            elif Board.complete_board[i][j].color == self.get_opponent_color():
                down_tiles = True
                end_i = i
                end_j = j
            i += 1
            j -= 1
            
        if down_tiles and down_end:
            self.flip_up_diagonal(row, column, end_i, end_j, down)
          
        up = 'up'
        up_tiles = False
        up_end = False
        end_i = 0
        end_j = 0
        i = row - 1
        j = column + 1
        while i >= 0 and j <= 7:
            if Board.complete_board[i][j].color == 'forest green':
                break
            elif Board.complete_board[i][j].color == self.get_your_color():
                up_end = True
                break
            elif Board.complete_board[i][j].color == self.get_opponent_color():
                up_tiles = True
                end_i = i
                end_j = j
            i -= 1
            j += 1

        if up_tiles and up_end:
            self.flip_up_diagonal(row, column, end_i, end_j, up)
        
        if (down_tiles and down_end) or (up_tiles and up_end):
            return True
        else:
            return False

    def flip_up_diagonal(self, row, column, end_row, end_column, direction):
        '''
        Method flip_up_diagonal
        parameters: row (int), column(int), end_row (int), end_column (int), and
        direction (string)
        returns: none
        does: flips the color all the diagonally placed tiles in between the new
        tile and a tile of that same color
        '''
        # flips tiles below new tile
        i = row + 1
        j = column - 1
        if direction == 'down':
            while i <= end_row and j >= end_column: 
                Board.complete_board[i][j].color = self.get_your_color()
                y = 155 - (i * 50)
                x = -175 + (j * 50)
                turtle.penup()
                turtle.speed(0)
                turtle.goto(x,y)
                turtle.pendown()
                turtle.color(Board.complete_board[i][j].color)
                turtle.begin_fill()
                turtle.circle(20)
                turtle.end_fill()
                i += 1
                j -= 1
        # flips tiles above new tile
        i = row - 1
        j = column + 1
        if direction == 'up':
            while i >= end_row and j <= end_column: 
                Board.complete_board[i][j].color = self.get_your_color()
                y = 155 - (i * 50)
                x = -175 + (j * 50)
                turtle.penup()
                turtle.speed(0)
                turtle.goto(x,y)
                turtle.pendown()
                turtle.color(Board.complete_board[i][j].color)
                turtle.begin_fill()
                turtle.circle(20)
                turtle.end_fill()
                i -= 1
                j += 1
                
    def get_points(self):
        '''
        Method get_points
        parameters: none
        returns:  nothing
        does: determines winner by counting number of black and white tiles, then
        prints out winner, and calls write_score_file to add user to leaderboard
        '''
     
        # counts number of black and white tiles and prints out winner and score
        P1_black = 0
        P2_white = 0
        for i in range(0, self.board_size):
            for j in range(0, self.board_size):
                if Board.complete_board[i][j].color == 'black':
                    P1_black += 1
                else:
                    P2_white += 1 
                
        if P1_black > P2_white:
            print("Player 1 is the winner!")
            print("Score~ Player 1:", P1_black, "Player 2:", P2_white)
            
        elif P1_black == P2_white:
            print("It's a draw!")
            print("Score~ Player 1:", P1_black, "Player 2:", P2_white)
           
        else:
            print("Player 2 is the winner!")
            print("Score~ Player 1:", P1_black, "Player 2:", P2_white)

        self.write_score_file(self.filename, P1_black)
        turtle.bye()
        sys.exit()
   
        
    def check_for_win(self):
        '''
        Methodcheck_for_win
        parameters: Boolean, True if game is not over
        returns: nothing
        does: determines if the game is over by checking if there are any green tiles
        left, if there are no more green tiles calls get_points and returns True, else
        returns False
        '''
        
        game_over = True
        for i in range(0, self.board_size):
            for j in range(0, self.board_size):
                if Board.complete_board[i][j].color == 'forest green':
                    game_over = False
                    return True
                    
        if game_over:
            print('Gameover!')
            self.get_points()
            
    
    def read_score_file(self, file):
        '''
        Function is read_score_file
        parameters: file (txt)
        returns: leader_list, a list of lists that contain 2 elements each,
        a username and score (strings)
        does: Opens up file to be read then creates and returns a list of lists
        from the lines, if file does not exist creates file, if file is empty
        creates empty list
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
            infile = open(file, 'w')
            infile.close()

            leader_list = []
        
            if os.stat(file).st_size == 0:
                return leader_list
        
            with open(file, "r") as outfile:
                scoreboard = outfile.read().splitlines()
        
            for i in scoreboard:
                leader = i.split()
                leader_list.append(leader)
                
            return leader_list


                
    def update_leaderboard(self, file, username, score):
        '''
        Function is update_leaderboard
        parameters: user_name (string), games_won (integer)
        returns: updated leader_list a list of lists that each contain
        a username and score (strings)
        does: makes a list with new user_name and the number of games_won
        and adds that list to the leader_list
        '''
        leader_list = self.read_score_file(file)
    
        if leader_list == []:
            leader_list.append(username +' '+ str(score))
        else:
            leader_list.append([username, str(score)])
        
        return leader_list


    def sort(self, lst):
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

    def rearrange_leaderboard(self, file, username, score):
        '''
        Function is rearrange_leaderboard
        parameters: user_name (string), games_won (integer)
        returns: list of lists
        does: uses sort to rearrange usernames in descending order
        based on score
        '''
        leader_list = self.update_leaderboard(file, username, score)
        self.sort(leader_list)
        return leader_list
 


    def write_score_file(self, file, score):
        '''
        Function is write_score_file
        parameters: file (txt), user_name (string), games_won (integer)
        returns: nothing
        does: rewrites file so that it contains an updated leaderboard with usernames
        listed from highest scores to lowest scores
        '''
        username = input('Enter in your username for the leaderboard! \n')
    
        # calls update_leaderboard to create leader_list
        # opens file to write and if leader_list just has one nested list it writes that one line
   
        leader_list =  self.update_leaderboard(file, username, score)
        if len(leader_list) == 1:
            with open(file, 'w') as outfile:
                outfile.write(' '.join(leader_list))
          
        # if the the leader_list has more than one entry rearrange the list and then rewrite file
        else:
            leader_list = self.rearrange_leaderboard(file, username, score)
            with open(file, 'w') as outfile:
                if len(leader_list) == 1:
                    outfile.write(' '.join(leader_list))
                else:
                    for lst in leader_list:
                        outfile.write(' '.join(lst))
                        outfile.write('\n')
  


class Board:
    complete_board = []

    def initialize_board(board_size):
        '''
        Method initialize_board
        parameters: board_size, int 
        returns: none
        does: creates a mult-dimentisonal list of tile objects, and assigns the
        initial 4 tiles with the correct colors
        '''
        # makes a multi-dimensional list of tile objects
        # i = rows and j = columns
        for i in range(0, board_size):
            row = []
            for j in range(0, board_size):
                row.append(Tile())
                
            Board.complete_board.append(row)
           
        # assigns tile colors to the 4 starting tiles
        for i in range((board_size//2) - 1, (board_size//2) + 2):
            for j in range((board_size//2) - 1, (board_size//2) + 2):
                if i == ((board_size//2) - 1):
                    if j == i:
                        Board.complete_board[i][j].color = 'white'
                    elif j == (i + 1):
                        Board.complete_board[i][j].color = 'black'
                if i == (board_size//2):
                    if j == (i - 1):
                        Board.complete_board[i][j].color = 'black'
                    elif j == i:
                        Board.complete_board[i][j].color = 'white'
        
def main():

    board_size = 8
    
    Board.initialize_board(board_size)

    draw_board(board_size)

    draw_starting_tiles(board_size)
    
    tile = Tile()
    turtle.onscreenclick(tile.get_coordinates)

main()
