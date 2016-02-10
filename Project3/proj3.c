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
    printf("Not enough arguments supplied\n")
  }
  FILE *in = fopen()
}
