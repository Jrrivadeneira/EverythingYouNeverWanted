/*Authors:
Chris Knapp
Connor Flanigan
Jack Rivadeneira
*/

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
    return -1;
  }

  FILE *in = fopen(inPath, "r");
  //Create the file if it does not exist, otherwise truncate and clear it
  FILE *out = fopen(outPath, "w+");
  //First index is the collumn, the second index is the row
  char inArray[][];
  //There are 1000 games, each game will have 43 entries, one for each spot on the board
  //There is also one entry for which player won, as well as a header line
  inArray = malloc(sizeof(char * (43*1001)));

  ssize_t read;
  size_t len = 0;
  char *line = NULL;
  char *token;

  int row = 0;
  int collumn = 0;
  //Throw away the first row, which should only be labels
  read = getline(&line, &len, in);
  while((read = getline(&line, &len, in)) != -1)
  {
    token = strtok(line, ",");
    inArray[collumn][row] = token;
    collumn++;
    while(token != NULL)
    {
      token = strtok(NULL, ",");
      inArray[collumn][row] = token;
      collumn++;
    }
    row++;
  }

  /* CONNOR DOES STUFF HERE */

  char outArray[][];
  row = 0;
  collumn = 0;
  for(row; row < 1000; row++)
  {
    for(collumn; collumn < 48; collumn++)
    {
      fprintf(out, "%c,", outArray[collumn][row]);
    }
  }
  return 0;
}
