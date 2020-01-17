import hanoi_viz

def move_tower(num_disks, towers, source, target, middle):
    '''
    Function is move_tower
    parameters:  num_disks (int), towers (dictionary), source, target and
    middle tower names (strings)
    returns: drawing of the steps taken to move the disks from source tower
    to target tower
    does: using recursion moves n - 1 disks from source tower to a middle
    (middle man) tower, then moves the nth disk from the source tower to
    the target tower, and lastly moves n - 1 disks from the middle tower
    to the target tower. 
    '''
    # base case that terminates the recursion and starts the visualization
    if num_disks == 1:
        hanoi_viz.move_disk(source, target, towers)
    else:
        # moves n - 1 disks to the middle tower using the target tower
        # as a helper
        move_tower(num_disks - 1, towers, source, middle, target)
        
        # moves the biggest block over to the target tower
        move_tower(1, towers, source, target, middle)
        
        # moves n - 1 disks to the target tower using the source tower
        # as a helper
        move_tower(num_disks - 1, towers, middle, target, source)

        
    
def main():

    # prompts the user to enter an integer 1-8 and reprompts them if they don't
    try:
        num_disks = 0
        while num_disks < 1 or num_disks > 8:
            num_disks = int(input("What is the number of disks (1-8)? \n"))
            
    except ValueError:
        num_disks = int(input("Please enter an integer (1-8)?"))

    # creates tower dictionary
    towers = hanoi_viz.initialize_towers(num_disks, 'A', 'B', 'C')
    
    # determines and visualizes steps for disks
    move_tower(num_disks, towers, 'A', 'B', 'C')
    
main()
