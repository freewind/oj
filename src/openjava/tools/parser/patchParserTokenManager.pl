#!/usr/local/bin/perl

while (<>) {
  print &removefinal($_);
}

sub removefinal {
  local($_) = @_;
  $_ =~ s/public final Token getNextToken/public Token getNextToken/;
  return $_;
}
