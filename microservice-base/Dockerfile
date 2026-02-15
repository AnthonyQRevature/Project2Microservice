FROM postgres:trixie

ENV JAVA_HOME=/usr/local/openjdk-25
ENV PATH=$JAVA_HOME/bin:$PATH

# Default to UTF-8 file.encoding
ENV LANG=C.UTF-8

ENV JAVA_VERSION=25

COPY openjdk-25*.tar.gz openjdk-25.tar.gz
RUN set -eux; \
	\
	mkdir -p "$JAVA_HOME"; \
	tar --extract \
		--file openjdk-25.tar.gz \
		--directory "$JAVA_HOME" \
		--strip-components 1 \
		--no-same-owner \
	; \
	rm openjdk-25.tar.gz; \
	\