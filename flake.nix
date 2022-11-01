{
  description = "Simple Java Flake"; # run `nix develop` to access nix shell
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils, ... }:
    flake-utils.lib.eachDefaultSystem (system:
      let pkgs = import nixpkgs {inherit system; }; 
      in rec{
        # This makes the shell 
        devShell = pkgs.mkShell {
	  nativeBuildInputs = [
	    pkgs.gradle # to be removed later
	  ];

	  buildInputs = [
	    pkgs.jdk11
	  ];
        };
      }
    );
}
