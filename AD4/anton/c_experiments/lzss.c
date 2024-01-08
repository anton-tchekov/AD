#include <stdio.h>

int main(int argc, char **argv)
{
	FILE *fp;
	char *fname_in, *fname_out;

	if(argc != 3)
	{
		fprintf(stderr, "Usage: ./lzss input-file output-file\n");
		return 1;
	}

	fname_in = argv[1];
	fname_out = argv[2];

	if(!(fp = fopen(fname_in, "rb")))
	{
		fprintf(stderr, "Failed to open `%s`\n", fname_in);
		return 1;
	}

	

	fclose(fp);
	return 0;
}
