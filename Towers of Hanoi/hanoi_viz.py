DISK = "X"

def move_disk(source, target, towers):
    ''' function move_disk
        parameters: name of source tower, name of target tower (strings), and
                    the towers themselves, a dictionary with key (string)
                    and value (list of strings to represent disks)
        returns: nothing

        does: modifies the given towers so that the source tower removes
              one disk (replaces with empty string), and adds one disk
              to the target tower. Calls print towers at the end so we
              illustrate the result of doing the move.

              About the input: towers is a dictionary. key = name of tower,
              and value = list of pegs. For example, we might have
              tower["A"] is a list with ["", "X", "X"], to represent
              that the tower with name A has two disks and one blank space.
    '''
    print("Moving disk from", source, "to", target)
    towers[source].remove(DISK)
    towers[source].insert(0, "")
    towers[target].remove("")
    towers[target].append(DISK)
    print_towers(towers, source)

def print_towers(towers, source):
    ''' function print_towers
        parameters: towers, a dictionary with key (string) and value (list
                    of strings to represent pegs), and source which is a string
                    holding the name of one tower
        returns: nothing

        does: prints the current state of the towers. Names of towers are
              printed acros the top, and then the pegs are printed to the
              correct overall height.
    '''                    
    for tower in towers:
        print(tower, "\t", end = "")
    print("")
    for i in range(len(towers[source])):
        for tower in towers:
            print(towers[tower][i], "\t", end = "")
        print("")
    print("\n")

def initialize_towers(num_disks, source, target, middle):
    ''' function initialize_towers
        parameters: initial height, plus names of source, target, middle towers
                    (all strings)
        returns: towers, a dictionary with key = name of tower and value
                 = pegs on that tower (all strings)
    '''
    towers = {source: [], target: [], middle: []}
    for i in range(num_disks):
        towers[source].append(DISK)
        towers[target].append("")
        towers[middle].append("")
    print_towers(towers, source)
    return towers
