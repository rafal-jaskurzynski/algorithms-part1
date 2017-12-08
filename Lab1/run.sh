CDIR=`pwd`

JPATH="$CDIR/algs4.jar:."
cd bin/

java -cp $JPATH  PercolationStats $@

# reading from file input:
# more percolation/input6.txt | ./run.sh stdio
