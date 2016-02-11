#include <stdio.h>
#include <stdlib.h>
#include <string.h>


int main(int argc, char *argv[])
{
  char *inPath;
  char *outPath;
  if (argc > 2)
  {
    inPath = malloc(strlen(argv[1]));
    strcpy(inPath, argv[1]);
    outPath = malloc(strlen(argv[2]));
    strcpy(outPath, argv[2]);
  }
  else
  {
    printf("Not enough arguments supplied\n");
  }
  FILE *in = fopen(inPath, "r");
  FILE *out = fopen(outPath, "w");
  //First index is the row, the second index is the collumn
  char inArray[][];
  //There are 1000 games, each with 5 attributes, leading to 5000 entries + the labels
  //This buffer should be large enough to accomodate the data set
  inArray = malloc(sizeof(char * 10000000));

}
